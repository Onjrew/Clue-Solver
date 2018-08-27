import sys
from itertools import zip_longest as zip

from constants import (
    CardType,
    SUSPECTS,
    WEAPONS,
    ROOMS,
)

from analyzer import GameStatusAnalyzer
from models import Player


class PlayerDisplayer(object):

    def _print_player_header(self, player):
        print(51 * '=')
        print('Player: %s\n' % player.name)

    def _print_card_header(self):
        hdr = '    {: <15}    {: <11}     {: <13}\n' \
              '    {:-<15}    {:-<11}     {:-<13}' \
              .format('Suspects', 'Weapons', 'Rooms', '', '', '')

        print(hdr)

    def _print_card_dict(self, cards):
        suspects = cards[CardType.SUSPECT]
        weapons = cards[CardType.WEAPON]
        rooms = cards[CardType.ROOM]

        cards_iterator = zip(suspects, weapons, rooms, fillvalue='')
        for suspect, weapon, room in cards_iterator:
            self._print_card_row(suspect, weapon, room)
        print()

    def _print_suggestions_played_on(self, suggestions_played_on):
        for suggestion in suggestions_played_on:
            self._print_card_row(
                suggestion[CardType.SUSPECT],
                suggestion[CardType.WEAPON],
                suggestion[CardType.ROOM]
            )
        print()

    def _print_card_row(self, suspect, weapon, room):
        row = '    {: <15}    {: <11}    {: <13}'.format(suspect, weapon, room)
        print(row)

    def display_player(self, player):
        self._print_player_header(player)

        print('Known Cards')
        self._print_card_header()
        self._print_card_dict(player.known_cards)

        print('Possible Cards')
        self._print_card_header()
        self._print_card_dict(player.possible_cards)

        # print('Suggestions Played On')
        # self._print_card_header()
        # self._print_suggestions_played_on(player.suggestions_played_on)


class CLIGameDisplayer(object):

    pass


class GameController(object):

    def __init__(self, displayer=CLIGameDisplayer()):
        pass


class Game(object):

    def __init__(self):
        self.players = []
        self.turn_number = 1
        self.suggestion = {}

        self.analyzer = None
        self.player_displayer = PlayerDisplayer()

    def get_acting_players(self, player):
        index = self.players.index(player)
        start = self.players[index + 1:]
        end = self.players[:index]
        return start + end

    def setup(self):

        num_players = int(input('Num Players: '))
        for i in range(num_players):
            player = Player(name='Player %d' % (i+1))
            self.players.append(player)

        self.analyzer = GameStatusAnalyzer(players=self.players)

        # Set the hidden state of the game
        from random import randint as r
        suspects = SUSPECTS.copy()
        weapons = WEAPONS.copy()
        rooms = ROOMS.copy()

        # Create solution
        solution = {
            CardType.SUSPECT: suspects.pop(r(0, len(suspects) - 1)),
            CardType.WEAPON: weapons.pop(r(0, len(weapons) - 1)),
            CardType.ROOM: rooms.pop(r(0, len(rooms) - 1)),
        }
        self.solution = solution

        # Deal hands
        hidden_players = [Player('hidden ' + str(i+1))
                          for i in range(num_players)]

        while len(suspects) > 0:
            player = hidden_players[r(0, len(hidden_players) - 1)]
            card = suspects.pop(r(0, len(suspects) - 1))
            player.known_cards[CardType.SUSPECT].append(card)

        while len(weapons) > 0:
            player = hidden_players[r(0, len(hidden_players) - 1)]
            card = weapons.pop(r(0, len(weapons) - 1))
            player.known_cards[CardType.WEAPON].append(card)

        while len(rooms) > 0:
            player = hidden_players[r(0, len(hidden_players) - 1)]
            card = rooms.pop(r(0, len(rooms) - 1))
            player.known_cards[CardType.ROOM].append(card)

        self.hidden_players = hidden_players

        for player in hidden_players:
            PlayerDisplayer().display_player(player)

        self.suggesting_player_index = 0

    # suggesting index
    # - Player does not appear in acting players
    # acting index
    # - start looping at suggesting player index + 1
    # acting index is a list of ints

    def loop(self):
        # Input Suggestion

        from random import randint as r
        import os
        while True:
            os.system('clear')

            # Display status
            # print(80 * '/')
            # print(self.solution)
            # for player in self.hidden_players:
            #     self.player_displayer.display_player(player)

            players = [self.players[i] for i in range(
                len(self.players)) if i != self.suggesting_player_index]
            for player in players:
                self.player_displayer.display_player(player)

            suggestion = {
                CardType.SUSPECT: SUSPECTS[r(0, 5)],
                CardType.WEAPON: WEAPONS[r(0, 5)],
                CardType.ROOM: ROOMS[r(0, 8)],
            }
            print('Turn', self.turn_number)

            turn_start_summary = ' '.join([
                'Player',
                self.suggesting_player_index + 1,
                'Suggestion:',
                suggestion
            ])
            print(turn_start_summary)

            acting_players = self.get_acting_players(
                self.players[
                    self.suggesting_player_index
                ]
            )
            for player in acting_players:
                ps = None
                while ps not in ['p', 's', 'x']:
                    print(player.name)
                    ps = input('p/s: ')

                if ps == 's':
                    self.analyzer.player_stopped(player, suggestion)
                    break

                if ps == 'p':
                    self.analyzer.player_passed(player, suggestion)

                if ps == 'x':
                    sys.exit()

            self.turn_number += 1
            self.suggesting_player_index += 1
            if self.suggesting_player_index >= len(self.players):
                self.suggesting_player_index = 0

        # Game over


def main(argv):

    # Setup
    game = Game()
    game.setup()

    # Start
    game.loop()


if __name__ == '__main__':
    sys.exit(main(sys.argv))
