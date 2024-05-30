package pl.hajduk.slashCommands.standardCommands.musicBotCommands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hajduk.service.audio.musicBot.PlayerManager;
import pl.hajduk.slashCommands.standardCommands.ICommand;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

public class Skip implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(Skip.class);

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "skip current song";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, "count", getDescription()));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.deferReply().queue();
        PlayerManager playerManager = PlayerManager.get();
        OptionMapping msg = event.getOption("count");

        int count = 1;
        if (msg != null) {
            count = event.getOption("count").getAsInt();
            if (count > 10) {
                event.replyEmbeds(new EmbedBuilder()
                        .setDescription("skipping songs must be less than 10")
                        .setColor(Color.RED)
                        .build()
                ).queue();
            }
        }

        for (int i = 0; i < count; i++) {
            playerManager.getGuildMusicManager(event.getGuild()).getTrackScheduler().getPlayer().stopTrack();

        }
        AudioTrack playingTrack = playerManager.getGuildMusicManager(event.getGuild()).getPlayer().getPlayingTrack();
        if (playingTrack != null) {
            log.info("path to track: " + playingTrack.getInfo().uri);

            event.getHook().sendMessage(MessageCreateData.fromEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.green)
                            .setDescription("Currently playing: " + Paths.get(playingTrack.getInfo().uri).getFileName().toString())
                            .build()
            )).queue();
        }


    }
}
