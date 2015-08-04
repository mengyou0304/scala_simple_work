package com.robin.grouping.computation

import com.robin.grouping.model.BasicSlot

import scala.collection.mutable.HashMap
import scala.io.Source

/**
 *
 * c: Channel
 * s/b: Slot/Blot
 * num: boundary based batch
 *
 *
 *  c1 c2 c3       c4 c5 c6 c7
 *  |     |        |        |
 *  +-----+        +--------+
 *    s1              s2
 *    |               |
 *    +---------------+
 *          b1
 *
 *  ================================
 *  s1   s2 s3      s4 s5 s6 s7 s8
 *  |        |      |            |
 *  +--------+      +------------+
 *  v1 num1  v2     v1   num2    v2
 *
 * So:
 *  v1,v2,v3,v4: Boundaries
 *  Num1,Num2  : slotNums in Batch
 *   c1,c2,c3->s1  :Slot
 *   s1,s2->b1     :Bolt
 *  How to spread s1~s3 into Num1: SlotSpreadingAlgorithm
 *  How to spread s1~s8 into Num1,Num2,Num3 : AssignmentAlgorithm
 *
 * Created by robinmac on 15-7-30.
 */
class SizeComputation(newAddedHDFSSizeURL: String, topicNum: Int, boltNum: Int) {

  val readHDFSSize =
    addAddtionalHDFSFile(_: HashMap[String, Long], newAddedHDFSSizeURL)

  def rebalance(oldMap: HashMap[String, Long]) = {


  }

  /**
   * Determine the slotnums for each boundary:
   *
   *
   * @param boundaries
   * @param infomap
   * @return
   */
  def assignSlotNums(boundaries: Array[Long],infomap:HashMap[String,Long]):Array[Long]={

  }
  def generateSlot(boundaries: Array[Long],infomap:HashMap[String,Long]):Unit={
    val slotnums=assignSlotNums(boundaries,infomap)


  }

  def satasticSizeInfo: Array[Long] = {
    val volums = Array(0l, 0l, 0l, 0l)
    val sizes = Array(0, 0, 0, 0)
    val dataSizeMap = readHDFSSize(new HashMap[String, Long])
    val bounds = Array(Integer.MAX_VALUE, dataSizeMap.values.sum / dataSizeMap.size, 200, -1)
    for (i <- 1 to bounds.length - 1)
      sizes(i) = dataSizeMap.toList.filter(
        v => v._2 match {
          case v2 if v2 > bounds(i) && v2 <= bounds(i - 1) => true
          case _ => false
        }
      ).map(v => {
        volums(i) += v._2;
      }
        ).size
    println("\nAll Channel Size: " + dataSizeMap.size)
    for (i <- 1 to bounds.length - 1)
      println("\n[" + bounds(i - 1) + "~~" + bounds(i) + "]Channel Num:" + sizes(i) + "  ChannelSize:" + volums(i))
    bounds
  }

  def addAddtionalHDFSFile(dataSizeMap: HashMap[String, Long], additionalURL: String): HashMap[String, Long] = {
    val lines = Source.fromFile(additionalURL).getLines().toList
    for (line <- lines) {
      val ss = line.split(" ")
      val key = getKey(ss(2).split("[/]"))
      if (!dataSizeMap.contains(key)) {
        dataSizeMap(key) = 0
      }
      val value = ss(0).toLong >> 20
      dataSizeMap(key) += value
    }
    println("Summed size: " + dataSizeMap.values.sum + "\n")
    dataSizeMap
  }

  def getKey(sArray: Array[String]): String =
    sArray(5) + "-" + sArray(6)
}

object SizeComputation extends App {
  val topicInfos = new TopicDescription("/Application/nla/log_pick/conf/test/testfile")
  val map = topicInfos.readFromFile();

  val sc = new SizeComputation("/Application/nla/log_pick/conf/test/size.out", 300, 100)
  val sizeMap = sc.readHDFSSize(new HashMap[String, Long])


  val boundaries = sc.satasticSizeInfo;
  //  if(!topicInfos.topicMapping.isEmpty)
  //    sc.rebalance(topicInfos.topicMapping)


}
