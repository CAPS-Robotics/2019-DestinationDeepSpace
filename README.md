# 2018-PowerUp
<h2>What is this?</h2>
This is the repository for the FRC2410 C++-to-Java robot rewrite.
Use the Java branch for converted code.

<hr>
<h2>Differences between Java and C++:</h2>
<table>
  <tr>
    <td></td>
    <th>C++</th>
    <th>Java</th>
  </tr>
  <tr>
    <th>Casing</th>
    <td>Uppercase first letter (e.g. "CamelCase()")</td>
    <td>Lowercase first letter (e.g. "camelCase()")</td>
  </tr>
  <tr>
    <th>Utility functions</th>
    <td>Simply call functions (e.g. "Wait(1.0)")</td>
    <td>All functions are methods in classes (e.g. "Timer.delay(1.0)")</td>
  </tr>
  <tr>
    <th>Digital inputs (also applies to outputs)</th>
    <td>1 (open), 0 (closed)</td>
    <td>True (open), False (closed)</td>
  </tr>
</table>
See more: http://first.wpi.edu/Images/CMS/First/WPILibUsersGuide.pdf

<hr>
<h2>Style Guide</h2>
(not final)<br>
Tabs, not spaces (spaces for alignment only)<br>
Spaces after if, for, while, switch, etc. but not after functions<br>
Brackets on new line<br>
e.g.:

```
function foo(bar)
{
  if (bar)
  {
    fooBar();
  }
}
```
