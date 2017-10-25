
from constants import CardType

class Player(object):

    def __init__(self, name):
        self.name = name
        self.possible_cards = {
            CardType.SUSPECT: [],
            CardType.WEAPON: [],
            CardType.ROOM: [],
        }
        self.known_cards = {
            CardType.SUSPECT: [],
            CardType.WEAPON: [],
            CardType.ROOM: [],
        }
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
