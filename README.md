# DeathPlus - Minecraft Plugin

Want to add a flavor to your game? Well, this plugin is definitely for you and your friends. Whenever you die,
your character can get a random potion effect, get kicked, or complete a MLG water bucket challenge. You should definitely
try it out and see how lucky you are.

# Permissions & Commands
Commands:
  - /deaths [player / none] - find out how many times you or other player died so far.
    Permissions: deathplus.deaths [Required]
    
Permissions for Staff:
  - deathplus.* - full access to plugin management.
  - deathplus.bypass - plugin will be disabled from group of players who has this permission.

# Useful Information
This plugin has a decent configuration at this stage(even more will be added).

* Language Support:
 This plugin's language set to English by default, but you can change the language in config.yml file. However, if there is no
 language in "languages" folder, you need to copy the en.yml file, rename it to your language (**IMPORTANT**: use the name of the file in config.yml), and 
 change default text to your language.
* Grace Period:
 In config.yml file you are able to delay the work of this plugin, if you want players to have some grace period before random
 events will happen to them, set **[graceEnabled]** to **[true]** (by default it's enabled), and specify how many times they can die
 before events happen, set **[graceLiveCount]** to **[number]** (by default 5)
* Effects & Events plugin includes:
 The chances of event happen, as well as effects, and their time are randomize.
 Here is the list:
  - 6% chance player will need to complete MLG water bucket challenge
  - 6 - 20% chance player will be kicked from server
  - 20 - 80% chance player will get bad potion effect, there is also a chance he/she can get multiple effects (20%)
  - player can respawn with a reduced amount of health and hunger bar.
  To make the game fun, if potion effect applied, usage of milk is disabled, but you can turn it off in config.yml, set
  [on-player-consume-milk] to [false].
  
# Support
If you need to report a bug or want to suggest a new feature, you can [open an issue ticket on GitHub](https://github.com/d3li0n/deathplus/issues).

# License
This project has been licensed under [GNU General Public License v3.0](https://github.com/d3li0n/deathplus/blob/master/LICENSE)

# Contributing
Want to help improve this plugin? There are numerous ways you can contribute.

If you'd like to make a financial contribution to the project, you can follow the Patreon link. If you can't make 
a donation, don't worry! There's lots of other ways to contribute:
 - Do you speak more than one language, and this language isn't supported by this plugin? Feel free to add this language
 by following origin language yml files, and make pull request.
 - Do you know Java? Feel free to improve the plugin, and blame the developer for bad code.
 - Do you run a server? If you find any bugs or typo in the plugin, feel free to open an issue and notify the developer
 about it.

***All contributors will be mentioned on this GitHub page.***