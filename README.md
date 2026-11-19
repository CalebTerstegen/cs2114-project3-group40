# cs2114-project3-group40

Small game where you serve customers as a barista.



How to run:

1. Download project
2. In eclipse open projects from file system and select download
3. Click on main and run as java application



Reflection:

• How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?

&#x09;A specific feature was not named in D3 however a feature that was added was the customers giving a tip based on how fast you completed the order. This feature only touched the controller class. 



• Did the change cross the model/view line?

The change did not cross the model/view line since as described before it was implemented only through the controller class. As a result it does not extend across the model/view line as it lies in neither. However, it should probably be moved later to model as this would be model logic.



• Did the view have to learn anything new about the model?
No. The tip feature was implemented in the controller


• If a change rippled through layers it shouldn't have, where and why?

N/A

• What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.

The main thing that changed was the addtion of the graphical UI

• What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.
N/A


Next Steps:

• What is left for the final build? Remaining features and fixes, in order.

&#x09;First the wrong ingredient system needs to be reworked to what was planned where getting it wrong reduces the tip bonus rather than giving a strike. Next some of the functionality in main and controller needs to be moved to the model to make it more independent.



• Which stretch goals are you committing to, and which are you dropping?

We still want to implement the mini game idea, but the main stretch goal we wanted to implement was the graphical UI

• What will your 3-minute demo show? The path you'll walk through, including the new feature.

We will show the how the graphical UI and how the game transformed from being a game played from the console to its own application

• Who does what? Which team member owns each remaining item.



Attribution:

|File|Who|How|
|-|-|-|
|Line|Caleb|Wrote|
|Order|Caleb|Wrote|
|Player|Caleb|Wrote|
|Customer|Caleb|Wrote|
|Controller|Sydney|Wrote \& GenAI-assisted|
|Main|Muna|Wrote \& GenAI-assisted|
|View|Muna|Wrote \& GenAI-assisted|
|Controller View|Muna|Wrote \& GenAI-assisted|
|Controller View Test|Muna|Wrote \& GenAI-assisted|
|README.md|Caleb|Contributed|
|README.md| Muna |Contributed \& GenAI-assisted|
|CustomerTest|Noah|Wrote|
|OrderTest|Noah|Wrote|
|LineTest|Noah|Wrote|
|PlayerTest|Noah|Wrote|
|Observer|Sydney|Wrote|



