Programming Assignment 1: Percolation


Answer these questions after you implement your solution.

/* *****************************************************************************
 *  What computer science and programming background do you have?
 *  Check all that apply.
 **************************************************************************** */
 [ ]  COS 126
 [ ]  COS 217
 [ ]  COS 226 (started, but did not complete)
 [ ]  ECE 115
 [x]  AP Computer Science A
 [x]  AP Computer Science Principles
 [x]  Passed COS placement exam
 [ ]  Algorithms, Part I on Coursera
 [ ]  Algorithms, Part II on Coursera
 [ ]  Competed in programming contests


/* *****************************************************************************
 *  What Java programming environment are you using?
 *  Check all that apply.
 **************************************************************************** */
 [x]  macOS
 [ ]  Windows
 [ ]  Linux
 [x]  IntelliJ
 [ ]  Eclipse
 [ ]  Visual Studio
 [ ]  Other (please specify) __________________________________


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */
public boolean[] grid: a boolean array representing the n-by-n grid, with each
site in the grid being represented by an element in the array (following the
rule that [index number of the element] = [row * n + col]). To account for the
virtual top and virtual bottom, the size of the array is n * n + 2. If an
element's value is true, the corresponding site is open. If the element's value
is false, the corresponding site is false.

public int n: an integer representing the side length of the n-by-n grid. I
made it public so that PercolationStats could access it for calculating the
confidence interval.

private QuickFindUF qf: a QuickFindUF object, an instance of the QuickFind
class. This is used in order to join two sets together using union, which
assists in marking surrounding (left/right/up/down) sites as full.

private int opens: an integer representing the number of open sites in the grid,
which is displayed in the visualizer, and is used in PercolationStats to find
the fraction of (open sites)/(total sites).


/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement the constructor
 *  and each method in the Percolation API.
 **************************************************************************** */

Percolation(): this is the constructor that takes one parameter (n). It sets
the instance variable for the dimension of the grid equal to n, intializes the
number of open sites to 0, initializes the grid array to be a size of n*n + 2
(to account for the virtual top and virtual bottom).

open(): this takes two parameters, the integers that represent the row and
column of the site to open. This site is checked for whether it is already open
using isOpen(), and if it is, no further action is needed to be done on it. If
the site is blocked, then it is opened (the value corresponding to that site in
the grid array is now true instead of false) and opens (the number of open sites)
is incremented. If the site is in the top row (row 0) of the grid, the site is
connected to the virtual top. Similarly, if the site is in the bottom row
(row n-1) of the grid, it is connected to the virtual bottom. For each direction
of left, right, up, and down, that corresponding site neighboring the specified
site is checked if it is a real valid site on the grid using validSite() and
if that neighboring site is also open. If both hold true, then the set
containing the neighboring site and the set containing the specified site are
joined using union() from QuickFindUF.

validSite(): takes two parameters, the integers that represent the row and column
of the site to check whether it is within the boundaries of an n-by-n grid. It
returns true if it is wihtin bounds, false if not.

isOpen(): takes two parameters, the integers that represent the row and column
of the site to check whether it is open. This returns the boolean value of the
corresponding element in the grid array (false if blocked, true if open).

isFull(): takes two parameters, the integers that represent the row and column
of the site to check whether it is full. This returns the boolean value of
whether the virtual top and specified site are connected using connected() from
QuickFindUF.

numberOfOpenSites(): returns the value of instance variable opens, which
represents the number of open sites in the grid.

percolates(): returns the boolean value of whether the virtual top and virtual
bottom are connected using connected() from QuickFindUF.



/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
200         16.808
215         22.094
280         63.610
276         59.053
277         60.502



/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */
I started with n=200, using the sample test case's value for PercolationStats.
Seeing that it only took around 16 seconds, I increased n slightly to 215 to get
a longer runtime. Now that I had two runs, I could apply the power law. Using
the equation T(n) = a * n^b seconds, I divided the two equations formed from the
experiment data and performed calculations to get that b is approximately
3.78109257. Using that, I plugged it back into the equation in power law form
using data from the first trial and found that a is around 3.35049226*10^-8.
From there, I set T equal to 60 and solved for n, giving n that is 280.019027,
so I then chose 280 to be the next n. Seeing that the result was greater than 60
seconds, I repeated the process of finding a and b, but this time using the 2nd
and 3rd trials. This resulting equation got me an n value of 275.943167 (chose
next n to be 276). Repeating the process once more, the equation gave an n value
of 276.851245, so I chose 277. Since the resulting runtime went over a minute,
the maximum n PercolationStats could handle for 100 trials under a minute was
276.


/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
200         0.150
1000        4.415
3461        188.796
2000        40.976
2500        76.088
2200        56.066
2215        56.349
2250        57.062
2300        59.520
2315        58.892
2350        61.416
2330        59.660
2338        62.940
2335        60.249
2333        62.572


/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 *  If it's the same strategy as for QuickFindUF, just write "same".
 **************************************************************************** */
I chose the same starting n value as I did for QuickFind just to see how they
would compare, and it seems like WeightedQuickUnion is significantly faster. I
then made chose a larger n-value of 1000, but it was still very fast, not even
taking 5 seconds. Taking the same approach as for the experiments using
QuickFind, I applied the power law and got an n-value of 3461.4698, so I
experimented with n = 3461 next. This resulted in a runtime of over 3 minutes,
so it showed me that its runtime does not obey the power law. I then tried 2000,
somewhere near the middle of the 2 previous n's. This gave a runtime closer to
60 seconds. I then increased the value of n (or decreased, if the runtime went
over 60 seconds) until I felt that I got substantially close to 60s. From my data,
the maximum n that PercolationStats could handle for 100 trials under a minute,
this time using WeightedQuickUnion instead of QuickFind.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
There is the backwash issue, where open sites connected to the bottom but
not connected to the top are marked as full.




/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
Percolation:
I could not figure out why sites that were supposed to be full were not, but
found out with the help of Jiatong in Office Hours that I was using else if's
to check the left/right/up/down sites neighboring the specified site instead of
using if's (using the else if's meant if there was one neighboring site that was
open, I would connect those two corresponding sites, but then the other potential
open surrounding sites would be ignored and therefore not marked as full).



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
It felt very good when each step my program worked because it took me a very
long time to get to the point where Percolation was working (figuring out to
use a virtual top and virtual bottom, plus much debugging, of course). I'm very
grateful for the PercolationVisualizer because it allowed me to see when I had
logical errors regarding the indices used as well as the errors of not filling
sites when they were supposed to. It was also interesting to see how the power
law could really apply when using it for the experiments.
