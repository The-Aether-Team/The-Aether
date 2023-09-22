![Banner image](assets/banner.webp)
# The Aether

The Aether Team presents the original Aether mod! Up to date for modern Minecraft versions and fully compatible with multiplayer. The Aether is a dimension of floating islands high in the sky, where you can begin a new survival adventure packed with new ores, mythical creatures and perilous Dungeons! All you need to do is construct a Glowstone frame and add water to light the mystical portal, and once you step in you’ll be transported immediately to the luscious hostile paradise of the Aether! The Aether Team has ensured to maintain the Aether as close to its original state from 2011 as possible, while still making improvements for modern versions. The team also continues to work on the mod's sequel, [The Aether II](https://github.com/The-Aether-Team/The-Aether-II).

## :heart: Support The Aether Team

[![Patreon](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.vercel.app%2Fapi%3Fusername%3DTheAetherTeam%26type%3Dpatrons&style=flat-square&logoColor=white)](https://patreon.com/TheAetherTeam)
[![Discord](https://img.shields.io/discord/118816101936267265.svg?label=discord&logoColor=FFFFFF&logo=discord&color=7289DA&style=flat-square)](https://discord.gg/aethermod)
[![Twitter](https://img.shields.io/badge/twitter-@DevAether-lightgrey?style=flat-square&logo=twitter&color=1DA1F2&logoColor=white)](https://twitter.com/DevAether)
[![Mastodon](https://img.shields.io/mastodon/follow/110581810287361848?domain=https%3A%2F%2Fmastodon.gamedev.place%2F&style=flat-square&logo=mastodon&logoColor=white&label=mastodon&color=858AFA)](https://mastodon.gamedev.place/@DevAether)
[![Threads](https://custom-icon-badges.demolab.com/badge/threads-devaether-green?logo=instagram-threads&style=flat-square&color=000000)](https://www.threads.net/@devaether)
[![Facebook](https://img.shields.io/badge/facebook-AetherMod-blue?logo=facebook&style=flat-square&color=1877F2&logoColor=white)](https://www.facebook.com/AetherMod)
[![YouTube](https://img.shields.io/badge/youtube-@DevAether-blue?color=FF0000&label=youtube&logo=youtube&style=flat-square)](https://www.youtube.com/@DevAether)
[![Twitch](https://img.shields.io/twitch/status/theaetherteam?logo=twitch&style=flat-square&logoColor=white)](https://www.twitch.tv/theaetherteam)
[![Reddit](https://img.shields.io/reddit/subreddit-subscribers/TheAether?color=FF4500&label=reddit&logo=reddit&style=flat-square&logoColor=white)](https://www.reddit.com/r/TheAether/)
[![wiki.gg](https://custom-icon-badges.demolab.com/badge/wiki.gg-aether-green?logo=wikigg&style=flat-square&color=FF1980)](https://aether.wiki.gg/)

If you enjoy our work, [please consider making a pledge](https://patreon.com/TheAetherTeam) today to help fund development. Every pledge goes directly into our development process and services, enabling us to continue making the Minecraft mods you know and love.

You can also support the Aether project and The Aether Team by telling your friends, joining our Discord server, and sharing our progress and announcements on social media. Every bit helps!

If you're interested in sponsoring the Aether project or The Aether Team, please [contact Oz#1986 on the Aether Community Discord](https://discord.gg/aethermod).

## :package: Download the latest releases
[![Modloader: Forge](https://img.shields.io/badge/mod%20loader-forge-CC974D?style=flat-square)](https://files.minecraftforge.net/net/minecraftforge/forge/)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/YhmgMVyu?color=00AF5C&logo=modrinth)](https://modrinth.com/mod/aether)
[![Modrinth Version](https://img.shields.io/modrinth/game-versions/YhmgMVyu?color=00AF5C&label=latest&logo=modrinth&last=true)](https://modrinth.com/mod/aether)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/255308.svg)](https://www.curseforge.com/minecraft/mc-mods/aether)
[![CurseForge Version](http://cf.way2muchnoise.eu/versions/255308_latest.svg)](https://www.curseforge.com/minecraft/mc-mods/aether)
[![CircleCI](https://circleci.com/gh/The-Aether-Team/The-Aether/tree/1.19.4-develop.svg?style=shield)](https://app.circleci.com/pipelines/github/The-Aether-Team/The-Aether?branch=1.19.4-develop)
### Release builds
The Aether has relatively stable beta builds available for release for 1.19.2, 1.19.4, and 1.20.1 on [Modrinth](https://modrinth.com/mod/aether) and [CurseForge](https://www.curseforge.com/minecraft/mc-mods/aether). A full 1.0.0 release will be coming soon.

### Bleeding edge builds
If you’re feeling a bit more adventurous or wish to help test the in-development versions, we provide **bleeding edge builds** which are produced on [CircleCI](https://app.circleci.com/pipelines/github/The-Aether-Team/The-Aether). These builds are created for every new commit and contain the latest available code. We do not recommend users treat these builds as releases, as they are unfinished and may contain serious issues. If you wish to download these builds, check out [this guide](https://github.com/The-Aether-Team/The-Aether/wiki/CircleCI-Guide).

### Packages
To install this mod through GitHub Packages in Gradle for development, you will need the [Gradle Github Packages Plugin](https://github.com/0ffz/gpr-for-gradle). To use it, make sure you have access to the Gradle plugins maven and the plugin as a buildscript dependency:

<details>
<summary> Buildscript Code</summary>

`settings.gradle`
```
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
```

`build.gradle`
```
plugins {
    id 'io.github.0ffz.github-packages' version '[1,2)'
}
```

</details>

Then you need to specify the package you want to use in your repository:

<details>
<summary> Repositories Code</summary>

```
repositories {
  ...
  maven githubPackage.invoke("The-Aether-Team/The-Aether")
}
```

</details>

Then load it through your dependencies, with `project.aether_version` specified in the `gradle.properties`:

<details>
<summary> Dependencies Code</summary>

```
dependencies {
  ...
  implementation fg.deobf("com.aetherteam.aether:aether:${project.aether_version}")
  ...
}
```

</details>

## :bug: Report bugs or other issues
If you're running into bugs or other problems, feel free to open an issue on our [issue tracker](https://github.com/The-Aether-Team/The-Aether/issues). When doing so, make sure to use one of the provided templates and fill out all the requested information. Make sure to keep your issue's description clear and concise. Your issue's title should also be easy to digest, giving our developers and reporters a good idea of what's wrong without including too many details. Failure to follow any of the above may result in your issue being closed.

## :wrench: Contribute to the project
Looking to contribute to the project? We ask that you read over our [Contributor's Guide](https://github.com/The-Aether-Team/The-Aether/blob/1.19.4-develop/docs/CONTRIBUTING.md) for more details as well as our [Contributor License Agreement (CLA)](https://github.com/The-Aether-Team/The-Aether/blob/1.19.4-develop/docs/AGREEMENT.md) before getting started.

Not sure what to help with? Take a look at our issue tracker for some ideas! [Here's a quick link](https://github.com/The-Aether-Team/The-Aether/labels/status%2Fhelp-wanted) which shows all the currently open issues that we'd love some help on.

## :scroll: License information
[![Asset license (Unlicensed)](https://img.shields.io/badge/assets%20license-All%20Rights%20Reserved-red.svg?style=flat-square)](https://en.wikipedia.org/wiki/All_rights_reserved)
[![Code license (LGPL v3.0)](https://img.shields.io/badge/code%20license-LGPL%20v3.0-green.svg?style=flat-square)](https://github.com/The-Aether-Team/The-Aether/blob/1.19.4-develop/LICENSE.txt)

If you're wanting to create a gameplay video/review, extension or addon, parody, or any other fan work of your own for The Aether, go for it! We love seeing the content our community creates, and we hope to make it as welcoming as possible for everyone. We ask however that you please don't advertise using our brand (our specific logo assets, team name, official social media posts).

If you are thinking about using the Aether project's code or assets, please note our licensing. **All assets of The Aether are unlicensed and all rights are reserved to them by The Aether Team and their respective authors.** The source code of The Aether mod for Minecraft 1.7+ is under the LGPL v3.0 license. Any previous versions' source code is unlicensed and all rights are reserved to it by The Aether Team.

If you have a reason that you wish to use our brand or any unlicensed material, please [get in contact with Oz#1986 on the Aether Community Discord](https://discord.gg/aethermod) for details.

## :star2: Special mentions
### :speech_balloon: Translations
[![Crowdin translations](https://img.shields.io/badge/crowdin-aether-blue?color=55BC5C&label=crowdin&logo=crowdin&style=flat-square)](https://translate.aether.devaether.com/)

The Aether is available in these languages thanks to the following contributors:

| Language                    | Locale  | Translators                                                                                                |
|-----------------------------|---------|------------------------------------------------------------------------------------------------------------|
| Arabic                      | (ar_sa) | Leopotion                                                                                                  |
| Austrian German             | (de_at) | Builderdog                                                                                                 |
| Brazilian Portuguese        | (pt_br) | Wanadi, Metal_knight, ZippetTV, CavaleiroDev, Jay_See, cacovil08, arthurdepaulafreitas, leparkorsz         |
| Chinese Simplified          | (zh_cn) | ETStareak, Parallel011, Haku98, minecraftXYZ                                                               |
| Chinese Traditional         | (zh_tw) | y830705                                                                                                    |
| Czech                       | (cs_cz) | Aerod, pomi108, bruno.sulc602                                                                              |
| Dutch                       | (nl_nl) | Critical, Morgan Hofmann, WarpedCarrot                                                                     |
| European French             | (fr_fr) | lololoustau, Beethov46, Aethyus, HidaSharker, ninjdai                                                      |
| German                      | (de_de) | HazeWorksOnFiveMServer, Catchears, A1403, DarkBread, KaiLeon8789, alleseinfacher, magiczocker, TotallyABot |
| Greek                       | (el_gr) | Manthos3gr                                                                                                 |
| Hungarian                   | (hu_hu) | HedSut                                                                                                     |
| Indonesian                  | (id_id) | F640                                                                                                       |
| Italian                     | (it_it) | fabiross096                                                                                                |
| Japanese                    | (ja_jp) | baguchan, falogonia, yukinosuke.tokyo                                                                      |
| Kazakh                      | (kk_kz) | snowcore                                                                                                   |
| Korean                      | (ko_kr) | minejango2, alligggg44, DataCenter1216, hyuzes, Cinam                                                      |
| Lithuanian                  | (lt_lt) | WaryRGMCA                                                                                                  |
| Malay                       | (ms_my) | thedoodlerhoodler2244                                                                                      |
| Mexican Spanish             | (es_mx) | Aer-ink, Kidi, Looxond                                                                                     |
| Polish                      | (pl_pl) | DarkKnightComes, Cinex20, damianek1984, mati.makosie, Pufferfish                                           |
| Romanian                    | (ro_ro) | lucatatar321, gigelciobanu205                                                                              |
| Russian                     | (ru_ru) | LeyxorCheysen, andreyalex1236, LunarP1, zombi1944, Foxgreat, lonespeaker, davekart0n, paskanius            |
| Slovak                      | (sk_sk) | GaucovyAero                                                                                                |
| Swedish                     | (sv_se) | magicus, 2lay                                                                                              |
| Thai                        | (th_th) | txwfyxa                                                                                                    |
| Toki Pona                   | (tok)   | leko soko Wijapipapa                                                                                       |
| Turkish                     | (tr_tr) | Dragunovi, egeyesss                                                                                        |
| Ukrainian                   | (uk_ua) | Nerkin, un_roman                                                                                           |
| Upside Down British English | (en_ud) | magiczocker                                                                                                |
| Welsh                       | (cy_gb) | Plague von Karma                                                                                           |

If you are interested in contributing translations to The Aether, check out our [Crowdin](https://translate.aether.devaether.com/) and our [Discord](https://discord.gg/aethermod)! If you have contributed translations in the past and your name is missing from the above list, please contact us.

### :hammer: Contributions
All of our code contribution credits can be found [here](https://github.com/The-Aether-Team/The-Aether/blob/1.19.4-develop/docs/CREDITS.txt). If you contributed to the project and do not see your name, please contact us.

### :file_folder: Dependencies
The Aether would not be possible without the features provided by the APIs and libraries it implements. Thanks goes to:
| Library | Information |
| ------- | ----------- |
| [![Curios](assets/dependencies/curios.webp)](https://www.curseforge.com/minecraft/mc-mods/curios) | Curios API was created by [C4](https://github.com/TheIllusiveC4).<br />The source can be found at https://github.com/TheIllusiveC4/Curios.<br />The dependency is licensed under [LGPL v3](https://www.gnu.org/licenses/lgpl-3.0). |
