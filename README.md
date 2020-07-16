## Introduction

The goal of the assignment is to build a single-screen app which shows the entered progress in the Circular Progress Bar.


## Visual Preview

Happy Flow |  Empty input Flow
:-------------------------:|:-------------------------:
![Alt Text](https://github.com/neetidotexe/Wheel/blob/master/CircularProgressBar1.gif)  |  ![Alt Text](https://github.com/neetidotexe/Wheel/blob/master/CircularProgressBar2.gif)

Inappropriate input flow (input > 100) |  Happy Transition between 2 inputs
:-------------------------:|:-------------------------:
![Alt Text](https://github.com/neetidotexe/Wheel/blob/master/CircularProgressBar3.gif)|![Alt Text](https://github.com/neetidotexe/Wheel/blob/master/CircularProgressBar4.gif)

## Requirements

### User flow
- User clicks the app icon
- Directly arrives at the Progress Bar Screen
- On the progress bar screen the user observes : 
	* Status bar Visibile
	* App bar with Title "My Application"  
	* A default background screen
	* An empty text field with hint "Enter Progress" (red underline "#f44336")
	* A button right of the empty text field with the label "Animate" (default button)
	* A grey circle ("#e0e0e0",24dp) with an Orange blob ("#f3c623", 60dp)
- By default cursor lands on empty text field & below the circle a numeric keyboard appears
- If you input a string of characters from keyboard and press "checkmark/enter" on numeric keyboard the numeric keyboard disappears. When you touch the text field it reappears. 
	* Only numeric values show up on the empty text field (i.e. inputs like minus (-), period(.) etc. do not reflect even when they are pressed) 
- After entering a input when you press Animate button following can happen
	* Inappropriate inputs : a relevant Toast appears at the bottom of screen. Following are the type of inappropriate inputs & toasts
		+ Input >100 & Animate button is pressed: "Progress can not be more than 100"
		+ Input is empty & Animate button is pressed : "Please enter a progress value"
	*  Appropriate input : For input values that after removal of preceding zeros fall between 0 & 100, animation appears. (thus 5,05,005, 0005 all are valid inputs.) Please note this preceding-zeroes case needs to be improved in future, since ideally we should only allow integers between 0 & 100 as input (which due to time constraints of exercise , we have not done).
		+ First time animation : Orange blob moves across circle periphery leaving a green trail from 0 to (Input/100)x360 degrees. E.g. 25 leads to 90 degrees from 0 movement
		+ Subsequent animations : Based on previous values 3 cases appear
			+ If the new input is > previous input : the green circle grows with blob appears moving forward
			+ If the new input is < previous input : the green circle reduces with blob appears moving backward
			+ If the new input is same as previous input : No visible change to view
- The progress value resets each time the app is quit 
 
 ### Constraints
 - Minimum SDK Version is 21
 - Supports only Portrait Mode

## Technology

- Used Git with a commit history
- Followed standard coding guidelines 
- Implemented Unit Tests
- Used Kotlin
- Used AndroidX and Architecture Components : ViewModel & LiveData 
