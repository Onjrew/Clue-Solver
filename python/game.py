import sys

from itertools import zip_longest as zip

from constants import (
    CardType,
    SUSPECTS,
    WEAPONS,
    ROOMS,
)

from models import (
    Player,
    Card,
    Suggestion,
)

from analyzer import GameStatusAnalyzer


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
        for suspect, weapon, room in zip(suspects, weapons, rooms, fillvalue=''):
            row = '    {suspect: <15}    {weapon: <11}    {room: <13}'.format(
                suspect=suspect,
                weapon=weapon,
                room=room
            )
            print(row)
        print()

    def _print_suggestions_played_on(self, suggestions_played_on):
        for suggestion in suggestions_played_on:
            row = '    {suspect: <15}    {weapon: <11}    {room: <13}'.format(
                suspect=suggestion[CardType.SUSPECT],
                weapon=suggestion[CardType.WEAPON],
                room=suggestion[CardType.ROOM]
            )
            print(row)
        print()

    def display_player(self, player):
        self._print_player_header(player)

        print('Known Cards')
        self._print_card_header()
        self._print_card_dict(player.known_cards)

        print('Possible Cards')
        self._print_card_header()
        self._print_card_dict(player.possible_cards)

        print('Suggestions Played On')
        self._print_card_header()
        self._print_suggestions_played_on(player.suggestions_played_on)


class Game(object):

    def __init__(self):
        self.players = []
        self.turn_number = 1
        self.suggestion = {}

        self.analyzer = None
        self.player_displayer = PlayerDisplayer()

    def setup(self):

        num_players = int(input('Num Players: '))
        for i in range(num_players):
            player = Player(name='Player %d' % (i+1))
            player.possible_cards = {
                CardType.SUSPECT: SUSPECTS.copy(),
                CardType.WEAPON: WEAPONS.copy(),
                CardType.ROOM: ROOMS.copy(),
            }
            self.players.append(player)

        self.analyzer = GameStatusAnalyzer(players=self.players)

    def loop(self):
        # Input Suggestion

        from random import randint as r
        while True:

            # Display status
            for p in self.players:
                self.player_displayer.display_player(p)

            suggestion = {
                CardType.SUSPECT: SUSPECTS[r(0, 5)],
                CardType.WEAPON: WEAPONS[r(0, 5)],
                CardType.ROOM: ROOMS[r(0, 8)],
            }
            print('Turn', self.turn_number)
            print('Suggestion:', suggestion)

            for p in self.players:
                ps = None
                while ps not in ['p', 's', 'x']:
                    print(p.name)
                    ps = input('p/s: ')

                if ps == 's':
                    self.analyzer.player_stopped(player, suggestion)
                    break

                if ps == 'p':
                    self.analyzer.player_passed(player, suggestion)

                if ps == 'x':
                    sys.exit()

        # Game over


def main(argv):

    # Setup
    game = Game()
    game.setup()

    # Start
    game.loop()


if __name__ == '__main__':
    sys.exit(main(sys.argv))
