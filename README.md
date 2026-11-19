# cs2114-project3-group40

Small game where you serve customers as a barista.



How to run:

1. Download project
2. In eclipse open projects from file system and select download
3. Click on main and run as java application



Reflection:

• How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?

&#x09;A specific feature was not named in D3 however since the graphical UI represented the largest missing part it can be considered as the feature added. With the addition of this feature no other classes were actually touched. This is because of the model/view separation allowing for the graphical UI view to be added without needing changes to other classes.



• Did the change cross the model/view line?

The change did not cross the model/view line since as described before with our model/view separation the graphical UI can be just added as a view. Then with it added as a view it can properly serve its function without touching any other classes.



• Did the view have to learn anything new about the model?



• If a change rippled through layers it shouldn't have, where and why?



• What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.



• What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.



Next Steps:

• What is left for the final build? Remaining features and fixes, in order.

&#x09;First the wrong ingredient system needs to be reworked to what was planned where getting it wrong reduces the tip bonus rather than giving a strike. Next some of the functionality in main and controller needs to be moved to the model to make it more independent.



• Which stretch goals are you committing to, and which are you dropping?



• What will your 3-minute demo show? The path you'll walk through, including the new feature.



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
|CustomerTest|Noah|Wrote|
|OrderTest|Noah|Wrote|
|LineTest|Noah|Wrote|
|PlayerTest|Noah|Wrote|
|Observer|Sydney|Wrote|



