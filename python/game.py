import os
import sys

from constants import (
    SUSPECTS,
    WEAPONS,
    ROOMS,
)

from analyzer import GameStatusAnalyzer


class Game(object):

    def __init__(self):
        self.analyzer = None
        self.players = []
        self.turn_number = 1
        self.suggestion = {}

    def setup(self):

        num_players = input('Num Players: ')
        for i in xrange(num_players):
            self.players.append(Player(name = 'Player %d' % (i+1)))
            print self.players[i].name

        self.analyzer = GameStatusAnalyzer(players=self.players)

    def loop(self):

        # Display status

        # Input Suggestion

        from random import randint as r
        while True:
            
            self.suggestion = {
                'suspect': SUSPECTS[r(0, 5)],
                'weapon': WEAPONS[r(0, 5)],
                'room': ROOMS[r(0, 8)], 
            }
            print 'Turn', self.turn_number
            print 'Suggestion:', self.suggestion
            
            for p in self.players:
                ps = None
                while ps not in ['p', 's', 'x']:
                    print p.name
                    ps = raw_input('p/s: ')
                if ps == 's':
                    # record stop
                    
                    # to to next turn
                    break

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
