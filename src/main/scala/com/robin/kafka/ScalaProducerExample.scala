package com.robin.kafka

import java.util.{Properties, Timer, TimerTask}

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}


class KafkaProducerConnection(topic: String = "testgroup",
                              brokers: String = "180.97.185.131:9092",
                              logPrintInterval: Int = 10000,
                              sleepWindow: Int = 100
                               ) extends Thread {
  var num = 0l;
  override def run(): Unit = {
    startTest;
  }

  def startTest = {
    val w = 10000
    val events: Long = 100000L * w
    val timer = new Timer();
    timer.schedule(new TimerTask {
      var lastnum = num;
      override def run(): Unit = {
        println("time in each seconds" + (num - lastnum))
        lastnum = num;
      }
    }, 1000l, logPrintInterval);
    val producer = init()
    for (nEvents <- 0l to events) {
      num = num + 1l;
      val msg = ",www.example.com," + nEvents;
      if (nEvents % 1000000L == 0) {
        println("[" + topic + "]Have sent messages: " + nEvents / 10000L + "00W")
        Thread.sleep(sleepWindow)
      }
      val data = new KeyedMessage[String, String](topic, msg);
      producer.send(data);
    }
    timer.cancel();
    producer.close();
  }
  def test(): Unit ={
    val p=new kafka.producer.DefaultPartitioner();
  }

  def init(): Producer[String, String] = {
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("producer.type", "async")
    props.put("partitioner.class","com.robin.kafka.ConfigBasedPartitioner")
    props.put("compression.codec", "snappy")
    props.put("queue.buffering.max.messages", "40000")
    props.put("batch.num.messages", "5000")
    val k=1024 * 1024 * 8
    props.put("send.buffer.bytes", k.toString)
    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)
    producer
  }
}
object ScalaProducerExample extends App {
  args.length match {
    case 4 => startprocess
    case _ => println("args.length is error as " + args.length)
  }

  def startprocess = for (i <- 1 to args(0).toInt) {
    val c1 = new KafkaProducerConnection(topic = "testtopic" + i, brokers = args(3), logPrintInterval = args(2).toInt, sleepWindow = args(1).toInt)
    c1.start
  }
}