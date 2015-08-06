package com.robin.grouping.model

import scala.collection.mutable.ArrayBuffer

/**
 *
 * Created by robinmac on 15-8-4.
 */
class Batch(infos: List[(String, Long)], slotNum: Long, startid: Long) {
//  println( toString())

  override def toString: String =
  "Batch: [" + startid + "~~" + (startid + slotNum - 1) + "]:\n"+
  "Infos Num:"+infos.length+"\n"+
  "Infos Weight: "+infos.map(_._2).sum/1024+"G\n\n"+
    assignSlotsInBatchAndGet().map(b=>{
      b.toString+"\n"
    })

  def assignSlotsInBatchAndGet(): ArrayBuffer[BasicSlot] = {
    val slotList = new ArrayBuffer[BasicSlot]
    for (id <- startid to slotNum + startid - 1) {
      slotList += new BasicSlot(id)
    }
    var k = 0
    var step = 1
    infos.sortWith((a,b)=>(a._2-b._2>0)).foreach(channel => {
      slotList(k).channelList += channel
      k += step
      if (k == slotList.size || k == (-1)) {
        step = (-step)
        k = k + step
      }
    })
    slotList
  }
}
