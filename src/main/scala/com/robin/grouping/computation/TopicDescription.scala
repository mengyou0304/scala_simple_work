package com.robin.grouping.computation

import kafka.utils.Logging

import scala.collection.mutable.{ArrayBuffer, HashMap}
import scala.io.Source

/**
 * Created by robinmac on 15-8-3.
 */
class TopicDescription(url: String) extends FileDesription(url: String, ".topic") {
}

class BoltDescription(url: String) extends FileDesription(url: String, ".group") {
}

class FileDesription(url: String, prefix: String) extends Logging{

  def readFromFile(fileurl: String = url): HashMap[String, Long] = {
    val map = new HashMap[String, Long]
    Source.fromFile(url).getLines().toList.foreach(v => {
      val ss = v.split("=")
      map += (ss(0) -> ss(1).toLong)
    })
    map
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

//object TopicDescription extends App {
//  val m = new TopicDescription("/Application/nla/log_pick/conf/test/testfile")
////  val map = m.transformToSlotMap(m.readFromFile())
//}
