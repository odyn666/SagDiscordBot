package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.LoggerFactory;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

public class Queue implements ICommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Queue.class);

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Songs in queue";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING,getName(),getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        PlayerManager playerManager=PlayerManager.get();
        // set commandEvent for scheduler to make him display a current track being played
//        playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().setCommandEvent(event);
        BlockingQueue<AudioTrack> queue = playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getQueue();
        StringBuilder tracks = new StringBuilder();
        AtomicInteger i = new AtomicInteger();

        // show top 15 tracks
        queue.forEach(track -> {
            if (i.get() >= 15) {
                return;
            }
            i.getAndIncrement();
            tracks.append(i).append(". ").append(track.getInfo().title).append("\n");
        });

        event.replyEmbeds(new EmbedBuilder()
                .setTitle("Tracks in queue:")
                .appendDescription(tracks.toString())
                .appendDescription(queue.size() > 10 ? ("\n and " + (queue.size()-10) + " more...") : "")
                .build()
        ).queue();

        LOGGER.info("used /queue command in {}", event.getChannel().getName());
    }
}
