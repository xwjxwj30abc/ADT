写入数据到clouder solr中：
代码运行前提：solr clouder 有对应的collection;可在solr.properties中修改collection

创建collection步骤:
1．生产实体配置文件:
solrctl instancedir --generate $HOME/solrtest
 #其中$HOME为自己设置的一个放置生成实体配置文件的目录，譬如我设置的为/home/solr;solrtest 为生成的这个配置的目录名称
 生成配置文件后会在solrtest/conf这个目录下产生很多配置文件，我们可以根据自己的需要修改schema.xml文件
 
2．创建 collection1 实例并将配置文件上传到 zookeeper:
 solrctl instancedir --create solrtest $HOME/solrtest
可以通过下面命令查看上传的实体：
 solrctl instancedir --list 
 
3．上传到 zookeeper 之后，其他节点就可以从上面下载配置文件。接下来创建 collection
solrctl collection --create solrtest -s 2-r 2
其中-s表示设置Shard数为2，-r表示设置的replica数为2。

４．查看collection:
http://xhadoop1:8983/solr/#/~cloud查看创建的Collection

修改Collection：
当我们创建Collection完成后，如果需要修改schema.xml文件重新配置需要索引的字段可以按如下操作：
1.如果是修改原有schema.xml中字段值，而在solr中已经插入了索引数据，那么我们需要清空索引数据集，清空数据集可以通过solr API来完成。
2.如果是在原有schema.xml中加入新的索引字段，那么可以跳过1，直接执行：

solrctl instancedir --update solrtest $HOME/solrtest   
solrctl collection --reload solrtest  

 
 


