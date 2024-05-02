package pl.hajduk;


import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.hajduk.GuildEvents.onJoin;
import pl.hajduk.Listeners.ModalListener;
import pl.hajduk.Listeners.TwatListener;
import pl.hajduk.config.FromJson;
import pl.hajduk.slashCommands.standardCommands.Clear;
import pl.hajduk.slashCommands.standardCommands.Zeus;
import pl.hajduk.slashCommands.testCommands.Ping;

import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) throws Exception {


        FromJson fJ = new FromJson();
        fJ.setPrefix("!");
        Random rnd = new Random();
        System.out.println(fJ.getPrefix());
        long seed = rnd.nextLong();

        rnd.setSeed(seed);

        int x = rnd.nextInt(1, 667);
        Boolean fuckedUP = true;
        String errors = "Throwing Errors at line: " + x;
        String komar = "hunting mosquitos";
        String acitvityMessage = "";
        if (x > 200) {
            acitvityMessage = errors;
        } else {
            acitvityMessage = komar;
        }
        if (fuckedUP) {
            acitvityMessage = "guys you fucked up badly";
        }


//        JDA jda=JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
//                .setActivity(Activity.playing(acitvityMessage)).build().awaitReady();

        JDABuilder builder = JDABuilder.createLight(fJ.getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);

        builder.setActivity(Activity.playing(acitvityMessage));
        // jda.addEventListener(new Zeus(),new Ping(),new ModalListener(),new Clear(),new onJoin());
        builder.addEventListeners( new Zeus(), new Ping(), new ModalListener(), new onJoin(), new TwatListener());

        builder.setEventPassthrough(true);
        builder.build().awaitReady();


        
        
        
        
       /* jda.updateCommands().addCommands(
                Commands.slash("say","say something").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL, Permission.MODERATE_MEMBERS))
        
        ).queue();*/


        //ConfigureMemoryUsage memoryUsage=new ConfigureMemoryUsage();
        //memoryUsage.configureMemoryUsage( jda);


    }
}