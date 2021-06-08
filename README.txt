Thoughts/Notes -
We're using an embedded H2 database so we can use the app without any external dependencies.

I have included a postman collection with the three requests (create bet, offer odds, get odds for a bet)

You will need to create a bet before offering odds for it.

Duplicate odds for the same bet (same odds & userId) will be rejected.

I'd have liked to do a bit more testing but ran out of time.

I would have split the odds validation into a separate Service, but given the simple acceptance criteria I decided to just do it within a @Pattern.

The '/odds/{betId}' endpoint doesn't seem very restful to me. I'd have preferred something like '/bets/{betId}/odds' or '/odds?betId={betId}'.