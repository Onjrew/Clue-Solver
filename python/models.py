from constants import (
    CardType,
    SUSPECTS,
    WEAPONS,
    ROOMS,
)


class Player(object):

    def __init__(self, name):
        self.name = name
        self.possible_cards = {
            CardType.SUSPECT: SUSPECTS.copy(),
            CardType.WEAPON: WEAPONS.copy(),
            CardType.ROOM: ROOMS.copy(),
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
