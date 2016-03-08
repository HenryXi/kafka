# Install Kafka on Linux
Kafka is a high-throughput distributed publish-subscribe messaging system that
can handle real-time data feed. In this series tutorials I will show you how to
use Kafka step by step. The first one is how to install Kafka on Linux. This
tutorial assumes there is no existing Kafka or ZooKeeper data on your computer.

**Download Kafka**

Download [Kafka](http://kafka.apache.org/) and un-tar it. (when I write this blog
0.9 is the latest)
```
tar -xzf kafka_2.11-0.9.0.0.tgz
cd kafka_2.11-0.9.0.0
```
**Start Zookeeper and Kafka**

Zookeeper is required for running Kafka. If you do not install Zookeeper you can
use the convenience script packaged with kafka to get a quick-and-dirty single-node
ZooKeeper instance. Open a terminal and type the command as bellow.
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
Open the second terminal type the command as bellow to start Kafka
```
bin/kafka-server-start.sh config/server.properties
```
My virtual machine is 1G RAM, I get following error message
```
Java HotSpot(TM) 64-Bit Server VM warning: INFO: os::commit_memory(0x00000000bae00000, 1073741824, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (malloc) failed to allocate 1073741824 bytes for committing reserved memory.
# An error report file with more information is saved as:
# /root/kafka_2.11-0.9.0.1/hs_err_pid5976.log
```
I change the maximum heap size (-Xmx)
```
export KAFKA_HEAP_OPTS="-Xmx256M -Xms128M"
```
run Kafka again, success;)

**Create a topic**

Open the third terminal to create a topick named ``test``
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```
Kafka save log in different server by ``partitions`` parameter. You can divide a
topic in multi-partition. The more partitions in your topic the more consumers can use.

**Send some messages**

we can send some message in command line client.
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
This is a message
This is another message
```
**Start a consumer**

we can also use command line consumer to get the message. Open the fourth terminal
and type the command.
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning
```
now you can see the message. Type some words in the third terminal you can get them
in fourth terminal. This is a sample producer(the third terminal) and consumer(the fourth
terminal).
happy learning.