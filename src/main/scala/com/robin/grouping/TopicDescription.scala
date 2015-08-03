package com.robin.grouping

/**
 * Created by robinmac on 15-8-3.
 */
class TopicDescription(url:String) extends FileDesription(url:String,".topic") {
}

class GroupingDescription(url:String) extends FileDesription(url:String,".group") {
}

object TopicDescription extends App {
  val m=new TopicDescription("/Application/nla/log_pick/conf/test/testfile")
  m.addChannel("abcdd",1l).writeToFile()
  m.addChannel("abcddd",1l);
}
