import os
import sys


SUSPECTS = ['Miss Scarlet', 'Colonel Mustard', 'Mr. Green', 'Mrs. Peacock', 'Professor Plum', 'Miss Orchid']
WEAPONS = ['Candlestick', 'Dagger', 'Lead Pipe', 'Revolver', 'Rope', 'Wrench']
ROOMS = ['Ballroom', 'Billiard Room', 'Conservatory', 'Dining Room', 'Hall', 'Kitchen', 'Library', 'Lounge', 'Study']


class Player(object):

    def __init__(self, name):
        self.name = name
        self.possible_cards = []
        self.known_cards = []
        self.suggestions_played_on = []


class Card(object):

    def __init__(self, card_type, name):
        self.card_type = card_type
        self.name = name
        self.state = None


class Suggestion(object):

    def __init__(self, suspect, weapon, location):
        self.suspect = suspect
        self.weapon = weapon
        self.location = location


class GameStatusAnalyzer(object):

    def __init__(self, players):
        self.players = players

    def remove_possible_card_from_player(self, player, card):
        player.possible_cards.remove(card)
        self.search_suggested_cards(player)

    def add_known_card_to_player(self, player, card):
        player.known_cards.append(card)
        self.remove_card_from_all_players(self.players, card)

    def remove_card_from_all_players(self, players, card):
        for player in players:
            self.remove_possible_card_from_player(player, card)

    def add_played_suggestion_to_player(self, player, suggestion):
        player.suggestions.append(suggestion)
    
    def search_suggested_cards(self, player):
        for suggestion in player.suggestions:
            # if only one card is knonwn, then
            # it belongs to this player
            continue
            self.add_known_card_to_player(player, card)

    

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
