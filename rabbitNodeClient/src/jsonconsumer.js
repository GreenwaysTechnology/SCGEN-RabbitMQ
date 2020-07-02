//import amqp client dependency

const amqp =  require("amqplib/callback_api");


//connect amqp
const connectionString = "amqp://localhost";
amqp.connect(connectionString,function(err,connection){

    if(err){
        throw err;
    }

    //QUeue Basic properties
   //create channel
   connection.createChannel(function(cherr,channel){
       if(cherr){
           throw err;
       }
       const QUEUE_NAME ='message1.queue';
       channel.assertQueue(QUEUE_NAME,{
           durable : true
       });
       console.log(" Waiting for Messages. If you want exit, you can Press CTRL+C");

       //consume message
       channel.consume(QUEUE_NAME,function(message){
             console.log("Got ", JSON.parse(message.content.toString()));
       },{
           noAck:true
       });

   });


});