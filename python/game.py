import sys

from itertools import zip_longest as zip

from constants import (
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
        suspects = cards['suspects']
        weapons = cards['weapons']
        rooms = cards['rooms']
        for suspect, weapon, room in zip(suspects, weapons, rooms, fillvalue=''):
            row = '    {suspect: <15}    {weapon: <11}    {room: <13}' \
                  .format(suspect=suspect, weapon=weapon, room=room)
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


class Game(object):

    def __init__(self):
        self.analyzer = None
        self.players = []
        self.turn_number = 1
        self.suggestion = {}

        self.player_displayer = PlayerDisplayer()

    def setup(self):

        num_players = int(input('Num Players: '))
        for i in range(num_players):
            player = Player(name='Player %d' % (i+1))
            player.possible_cards = {
                'suspects': SUSPECTS.copy(),
                'weapons': WEAPONS.copy(),
                'rooms': ROOMS.copy(),
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
                'suspect': SUSPECTS[r(0, 5)],
                'weapon': WEAPONS[r(0, 5)],
                'room': ROOMS[r(0, 8)],
            }
            print('Turn', self.turn_number)
            print('Suggestion:', suggestion)

            for p in self.players:
                ps = None
                while ps not in ['p', 's', 'x']:
                    print(p.name)
                    ps = input('p/s: ')
                if ps == 's':
                    # record stop

                    break

                if ps == 'p':
                    # Remove cards from player's possible cards
                    if suggestion['suspect'] in p.possible_cards['suspects']:
                        p.possible_cards['suspects'].remove(suggestion['suspect'])

                    if suggestion['weapon'] in p.possible_cards['weapons']:
                        p.possible_cards['weapons'].remove(suggestion['weapon'])

                    if suggestion['room'] in p.possible_cards['rooms']:
                        p.possible_cards['rooms'].remove(suggestion['room'])

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
