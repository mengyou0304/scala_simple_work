package com.robin.grouping

import java.io.PrintWriter

import scala.collection.mutable.{ArrayBuffer, HashMap}
import scala.io.Source

/**
 * Created by robinmac on 15-8-3.
 */
class TopicDescription(url: String) extends FileDesription(url: String, ".topic") {
}

class GroupingDescription(url: String) extends FileDesription(url: String, ".group") {
}

class FileDesription(url: String, prefix: String) {

  def readFromFile(fileurl: String = url): HashMap[String, Long] = {
    val map = new HashMap[String, Long]
    Source.fromFile(url).getLines().toList.foreach(v => {
      val ss = v.split("=")
      map += (ss(0) -> ss(1).toLong)
    })
    map
  }

  def writeToFile(fileurl: String = url, map: HashMap[String, Long]): Unit = {
    val out = new PrintWriter(fileurl + prefix)
    map.foreach(v => out.println(v._1 + "=" + v._2))
    out.close()
  }

  def transformToSlotMap(map: HashMap[String, Long]): HashMap[Long, ArrayBuffer[String]] = {
    val res = new HashMap[Long, ArrayBuffer[String]]
    map.foreach(v => {
      if (!res.contains(v._2))
        res(v._2) = new ArrayBuffer[String]
      res(v._2) += v._1
    })
    res
  }

  def transformToTopicMap(map: HashMap[Long, ArrayBuffer[String]]): HashMap[String, Long] = {
    val res = new HashMap[String, Long]
    map.foreach(v => {
      v._2.foreach(k => {
        res(k) = v._1.toLong
      })
    })
    res
  }

  /**
   * The method that add a new channel with long.size of data per day.
   * @param map
   * @param channelInfo
   */
  def addChannel(map: HashMap[Long, ArrayBuffer[String]], channelInfo: (String, Long)): Unit = {
    if (map.isEmpty)
      throw new RuntimeException("Haven't read From ConfigFile")
   //TODO finish this
  }

}

object TopicDescription extends App {
  val m = new TopicDescription("/Application/nla/log_pick/conf/test/testfile")
  val map = m.transformToSlotMap(m.readFromFile())
}
