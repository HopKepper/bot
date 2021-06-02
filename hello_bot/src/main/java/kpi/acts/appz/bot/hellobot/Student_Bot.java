package kpi.acts.appz.bot.hellobot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import kpi.acts.appz.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

//consumer
public final class Student_Bot extends Bot {
    public static void main(String[] args) throws IOException, TimeoutException {

        ApiContextInitializer.init();
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("lecture", false, false, false, null);
        channel.basicConsume("lecture", true, (consumerTag, message) -> {
            String m = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("I have just received a message = " + m);
            try {
                Bot.runBot(new Student_Bot("1880111321:AAEt7QTlDnNQ6N5hzdadD3FAjIpeloL-NBk", "BootyBot"));
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }, consumerTag -> {
        });
        System.out.println("I have just received a message =");
    }

    Student_Bot(String token, String botName) throws IOException, TimeoutException {
        super(token, botName);
        ApiContextInitializer.init();
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("lecture", false, false, false, null);
        channel.basicConsume("lecture", true, (consumerTag, message) -> {
            String m = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("I have just received a message = " + m);
            try {
                Bot.runBot(new Student_Bot("1880111321:AAEt7QTlDnNQ6N5hzdadD3FAjIpeloL-NBk", "BootyBot"));
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }, consumerTag -> {
        });
        System.out.println("I have just received a message =");
    }

    @Override
    protected void processTheException(Exception e) {
        e.printStackTrace();
        System.out.println(e.toString());
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        sendTextMessage(update.getMessage(), "приєднався до лекції ");
    }
}

