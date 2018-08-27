
class GameStatusAnalyzer(object):

    def __init__(self, players):
        self.players = players

    def remove_possible_card_from_player(self, player, card_type, card):

        if card in player.possible_cards[card_type]:
            player.possible_cards[card_type].remove(card)

    def add_known_card_to_player(self, player, card):
        player.known_cards.append(card)
        self.remove_card_from_all_players(self.players, card)

    def remove_card_from_all_players(self, players, card):
        for player in players:
            self.remove_possible_card_from_player(player, card)
            self.search_suggested_cards(player)

    def add_played_suggestion_to_player(self, player, suggestion):
        player.suggestions.append(suggestion)

    def search_suggested_cards(self, player):
        print('Searching suggested cards...')
        for suggestion in player.suggestions_played_on:
            # if only one card is known, then
            # it belongs to this player
            continue
            # self.add_known_card_to_player(player, card)

    def player_stopped(self, player, suggestion):
        # record stop
        player.suggestions_played_on.append(suggestion)

    def player_passed(self, player, suggestion):
        # Remove cards from player's possible cards
        for card_type, card in suggestion.items():
            self.remove_possible_card_from_player(player, card_type, card)
        self.search_suggested_cards(player)
