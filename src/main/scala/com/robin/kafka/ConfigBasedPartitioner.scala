package com.robin.kafka

import kafka.producer.Partitioner
import kafka.utils.VerifiableProperties

import scala.collection.immutable.HashMap
import scala.io.Source

/**
 * Created by robinmac on 15-7-30.
 */
class ConfigBasedPartitioner (props: VerifiableProperties = null)extends  Partitioner {
  var map = new HashMap[String, Int]
  var map2= new HashMap[String,String]
  map2+="a"->"b"
  //  val url=props.getString("partition.config.url")
  val url = "/Application/nla/log_pick/conf/test.out"
  val lines: List[String] = Source.fromFile(url).getLines().toList
  for (line <- lines) {
    val tm = line.split("=")
    map += (tm(0) -> tm(1).toInt)
  }
  println("After reading the configruration,we get partition Map:\n"+map)


  override def partition(key: Any, numPartitions: Int): Int = {
    map.get(key.toString).getOrElse(0)
  }
}
object ConfigBasedPartitioner extends App{
  val k=new ConfigBasedPartitioner();
}


