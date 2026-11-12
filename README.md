# cs2114-project3-group40

Small game where you serve customers as a barista.



Reflection:



• What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?

First the timing element was moved from the line class to the controller as it made more sense. Furthermore, the submit step method of line was not added as it was an overreach and can just be fulfilled by correctly interacting with the underlying customer's orders. Also, the line class was made into a modified queue class rather than using one as a field. 



• Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?
The model was designed to function independently of the view, with the Line, Customer, Order and Player classes responsible for managing the game's logic and data. The Controller handles the communication between the classes and UI. Although some responsibilities changed from our original spec, the model doesn't need the UI to manage customer orders, queue operations or player statistics. 


• What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?

We have figured out how to check whether steps are being correctly followed. This is handled in the order class. However, we still do not know the proper order of a latte.



• What doesn't work yet? 

MVP features, bad-input cases, or tests that are missing or failing. Ingredient processing and customer timing still have to be integrated with the model. 

Next steps:

• What is left to finish the MVP? 
The remaining features and fixes, in the order you will do them.

Update and rerun the JUnit tests to match the implemented API; fix any remaining failures.




• Your change feature. Deliverable 4 requires you to add one non-trivial feature that was not in your MVP. Name it, then predict which classes it will touch (model, view, controller) and which ones your design says should not need to change. You will compare this prediction to what actually happened.





• Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.

For the stretch goals we will attempt to modify the money system such that playing better rewards higher money. Such as fulfilling orders quickly and correctly. 



• What new tests will you need? The methods and bad-input cases the added work must cover.
Timer/controller behavior: Test time remaining during normal play, expiration at the boundary, and invalid elapsed-time inputs if that method accepts them.
Recipe completion: Test a fully correct ingredient sequence, a wrong step that resets progress, and an attempt made after an order is already complete.
Payout feature: Test the base payout, a fast/correct completion bonus, reduced or no bonus after mistakes, and invalid payout inputs such as negative values.
Integration: Test that a completed order updates money and advances the queue; a timeout gives a strike; and three strikes end the game.

GenAI Note:
GenAI was used as a supporting tool throughout the development of this project, primarily to help with understanding Java concepts and implementing the Controller and Main components. Provided guidance on connecting the Controller to the model and handling customer input. Also suggested a reset timer method in the Controller class. 

Who does what? Which team member owns each remaining item.
Attribution:

| File | Who | How |
| --- | --- | --- |
| Line | Caleb | Wrote |
| Order | Caleb | Wrote |
| Player | Caleb | Wrote |
| Customer | Caleb | Wrote |
| Controller | Sydney | Wrote & GenAI-assisted|
| Main | Muna | Wrote & GenAI-assisted |
| README.md | Caleb, Sydney | Contributed |
| CustomerTest | Noah | Wrote |
| OrderTest | Noah | Wrote |
| LineTest | Noah | Wrote |
| PlayerTest | Noah | Wrote |
| Observer | Sydney | Wrote |
