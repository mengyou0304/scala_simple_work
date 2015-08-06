package com.robin.grouping.model

import scala.collection.mutable.ArrayBuffer

/**
 * Created by robinmac on 15-8-4.
 */
class BasicSlot(slotid:Long) {
  /**
   * The form like this
   *
   * topic1: channelid-userid1,channelid-userid2,channelid-userid3
   * topic2: channelid-userid4,channelid-userid5,channelid-userid6
   */
  val channelList=new ArrayBuffer[(String,Long)]

  def transformToTopicMap():Iterable[(String,Long)] = channelList.map(_._1->slotid)

  def getDataSize():Long = channelList.map(_._2).sum


  override def toString = "[slotid]:"+slotid+" [ChannelNums]:"+channelList.size+" [DataSize]: "+getDataSize()
}

class TopicSlot(slotid:Long) extends BasicSlot(slotid:Long){
}

class BoltSlot(slotid:Long) extends BasicSlot(slotid:Long){

}
