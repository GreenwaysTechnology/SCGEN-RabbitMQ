const amqp = require('amqplib/callback_api');
const cluster = require('cluster');
const numCPUs = require('os').cpus().length;

if (cluster.isMaster) {
    console.log(`Master ${process.pid} is running`);
  
    // Fork workers.
    for (let i = 0; i < numCPUs; i++) {
      //will spawn many processes.
      cluster.fork();
    }
  
    cluster.on('exit', (worker, code, signal) => {
      console.log(`worker ${worker.process.pid} died`);
    });
  } else {
    // Workers can share any TCP connection
    amqp.connect('amqp://localhost', function(error, connection) {
        connection.createChannel(function(error, channel) {
            var queue = 'task_queue';
    
            channel.assertQueue(queue, {
                durable: true
            });
            //give only one message to one worker
            channel.prefetch(1);
            console.log(`[${process.pid}] Waiting for messages in %s. To exit press CTRL+C`, queue);
            channel.consume(queue, function(msg) {
                var secs = msg.content.toString().split('.').length - 1;
                //console.log(`Worker ${process.pid} started`);

                console.log(`[${process.pid}] Received %s`, msg.content.toString());
                //Simultation of long running task.
                setTimeout(function() {
                    console.log(`${process.pid} done`);
                    //send ack to rabbitmq , in order to remove message
                    channel.ack(msg);
                }, secs * 1000);
            }, {
                noAck: false
            });
        });
    });
  
  }

