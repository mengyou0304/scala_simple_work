package com.robin.grouping.model

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, HashMap}

/**
 * Created by robinmac on 15-8-4.
 */
class BasicSlot {
  /**
   * The form like this
   *
   * topic1: channelid-userid1,channelid-userid2,channelid-userid3
   * topic2: channelid-userid4,channelid-userid5,channelid-userid6
   */
  val map: HashMap[Long, ArrayBuffer[String]]=new mutable.HashMap[Long, ArrayBuffer[String]]

}

class TopicSlot extends BasicSlot{
}

class BoltSlot extends BasicSlot{
}
