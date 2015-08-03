package com.robin.grouping.computation

import scala.collection.mutable

/**
 * Created by robinmac on 15-8-3.
 */
class WeightColumn (id:Int){
  val map=new mutable.HashMap[String,Long]

  def +(channelInfo:(String,Long)): this.type ={
    map+=channelInfo
    this
  }
  def sumWeight()=map.values.sum

  override def toString: String = {
    val s = new StringBuffer("")
    map.foreach(v=>s.append(v._1.toString+"\t"+v._2+"\n"))
    Array(0,1,2,3,4).foreach (println _)
    map.foreach(println(_))

    s.append("[Summed Weight]: "+sumWeight)
    s.toString
  }
}

object WeightColumn extends App{
  val m=new WeightColumn(1)
  val string=m+("abbc-121",12l)+("mmm",13l)
  println(string)


}

