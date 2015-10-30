<div align="center"><h1>RecycleRush-MAIN STEMly Competition</h1></div>
<hr/>

<h3>Steps for Integration:</h3>
<hr/>
<h5>Step #1:</h5> Download the repository, through either:
<ul>
<li>Git Shell:</li>
<code>git clone https://github.com/frc4976/RecycleRush-MAIN.git</code>
<li>Github for Windows:</li>
<a href="github-windows://openRepo/https://github.com/frc4976/RecycleRush-MAIN"><img src="http://i.imgur.com/rcBtyhA.png"/></a>
<li>ZIP Download:</li>
<a href="https://github.com/frc4976/RecycleRush-MAIN/archive/master.zip"><img src="http://i.imgur.com/sS7AW0l.png"/></a>
</ul>
<h5>Step #2:</h5> Create IntelliJ Project
<ul>
<li>Start a new project:</li>
<img src="http://i.imgur.com/sJWdeQf.png"/>
<li>Make sure to use Java 1.8 (look up an online tutorial if this does not show up for you):</li>
<img src="http://i.imgur.com/57IvZjl.png"/>
<li>Use these settings:</li>
<img src="http://i.imgur.com/kX7fJlp.png"/>
</ul>
<h5>Step #3:</h5> Add WPILib to Project
<ul>
<li>Go to File > Project Setings / Project Structure and select the src module to add JARs and directories</li>
<img src="http://i.imgur.com/zNBFuxZ.png"/>
<li>Navigate to and add C:\Users\<username>\wpilib\java\current\lib\ or equivalent</li>
<img src="http://i.imgur.com/vOEwTe4.png"/>
</ul>
<h5>Step #4:</h5> Add build.xml as an Ant Build File
<ul>
<li>Right-Click build.xml and select add as an Ant Build File</li>
</ul>

You know have a completely ready and clean FRC robot project! No extra .classpath or .project files that mess up other users, just source!
