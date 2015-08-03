package com.robin.grouping

import java.io.PrintWriter

import scala.collection.mutable
import scala.io.Source

/**
 * Created by robinmac on 15-8-3.
 */
class FileDesription(url:String,prefix:String) {
  val topicMapping=new mutable.HashMap[String,Long];
  readFromFile()


  def readFromFile(): this.type ={
    Source.fromFile(url).getLines().toList.foreach(v=>{
      val ss=v.split("=")
      topicMapping+=(ss(0)->ss(1).toLong)
    })
    this
  }

  def writeToFile(): this.type ={
    val out=new PrintWriter(url+prefix);
    //    out.println(topicMapping.foreach(v=>{v._1+"="+v._2}+"\n"))
    topicMapping.foreach(v=>out.println(v._1+"="+v._2))
    out.close()
    this
  }

  def addChannel(channelInfo:(String,Long)): this.type ={
    if(topicMapping.isEmpty)
      throw new RuntimeException("Haven't read From ConfigFile")
    if(topicMapping.contains(channelInfo._1))
      throw new RuntimeException("Can't add an already exist Channel");
    topicMapping+=channelInfo
    this
  }

}
