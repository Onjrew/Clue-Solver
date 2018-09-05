from game import Game
from player import Player


class TestGame(object):

    def test_game_initialization(self):
        game = Game()

        assert game.players == []
        assert game.main_player is None

    def test_add_player_to_the_game(self):
        player = Player(name='foo bar')

        game = Game()
        game.add_player(player)

        assert game.players == [player]
