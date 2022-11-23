package kea.dilemmaspilbackend.game.service;

import kea.dilemmaspilbackend.game.model.Player;
import kea.dilemmaspilbackend.game.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameServiceTest {
    GameService gameService;
    GameRepository gameRepository;

    @BeforeEach // kaldes før hver @Test
    public void setUp()  {
        gameRepository = new GameRepository();
        gameService = new GameService(gameRepository);
    }

    @Test
    public void testCreateGameLobby(){
        // Tests if a lobby is added to the game lobby list by checking if the lobby code works as a key in the list.
        Player player = new Player();
        String lobbyCode = gameService.createGameLobby(player);
        Assertions.assertNotNull(gameRepository.getGameLobbyList().get(lobbyCode));
    }

    @Test
    public void testJoinGameLobby(){
        // Tests a player joining a game lobby. If the player exists in the player list of the lobby, the test is successful
        Player player = new Player();
        player.setName("Thomas");
        String lobbyCode = gameService.createGameLobby(player);
        gameService.joinGameLobby(player, lobbyCode);
        Assertions.assertEquals(gameRepository.getGameLobbyList().get(lobbyCode).getPlayerList().get(0).getName(), player.getName());
    }

    @Test
    public void testRemoveGameLobby(){
        // Tests if a lobby is removed from the list of active games. If the lobby is removed, the test is successful.
        Player player = new Player();
        String lobbyCode = gameService.createGameLobby(player);
        gameService.removeGameLobby(lobbyCode);
        Assertions.assertNull(gameRepository.getGameLobbyList().get(lobbyCode));
    }

    @Test
    public void testLeaveGameLobby(){
        // Tests if a player has left a game lobby. If the player leaves, it's a success
        Player player = new Player();
        player.setName("Thomas");
        String lobbyCode = gameService.createGameLobby(player);
        gameService.leaveGameLobby(player, lobbyCode);
        Assertions.assertTrue(gameRepository.getGameLobbyList().get(lobbyCode).getPlayerList().isEmpty());
    }

    @Test
    public void testReadyUp(){
        // Tests if a player in a lobby has readied up.
        Player player = new Player();
        player.setName("Thomas");
        String lobbyCode = gameService.createGameLobby(player);
        gameService.readyUp(player, lobbyCode);
        Assertions.assertTrue(gameRepository.getGameLobbyList().get(lobbyCode).getPlayerList().get(0).isReady());
    }
}
