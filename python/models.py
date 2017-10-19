
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
