
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
