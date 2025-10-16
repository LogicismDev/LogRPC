# LogRPC

A Rich Presence program to show your activity on Discord

[Download for Windows (setup)](https://logicism.tv/downloads/LogRPC-setup.exe) - [Download for Windows (zip)](https://logicism.tv/downloads/LogRPC-v2.32-win64.zip) - [Download for macOS (dmg)](https://logicism.tv/downloads/LogRPC-v2.32-mac.dmg) - [Download for Linux (zip)](https://logicism.tv/downloads/LogRPC-v2.32-linux.zip) - [Download (Mirror)](https://mega.nz/folder/EIhkjCIR#Ck5IlMZeEtjVvBYjLNqrRw) (v2.41)

**Java 8 or higher is required in order to run LogRPC!**

* Presence Types:
    * Manual:
      - Default (config.yml) w/ Presets
      - Customizable Presence
      - Game Consoles:
          - Nintendo 3DS
          - Wii U
          - Nintendo Switch
          - Nintendo Switch 2
      - PC Games:
         - Overwatch 2
      * Credits go to: 
         * ninstar on GitHub for Nintendo Console Discord Presences
         * Tominous on GitHub for Overwatch Discord Presence Application IDs
      
    * Program (Windows Only):
      * Uses Windows User32 Library to grab the current window title and process name that the user is using currently.
         - Adobe (Photoshop, Illustrator, Indesign, After Effects, Premiere Pro, Dreamweaver, Acrobat Reader)
         - Beat Saber
         - Emulators (DeSmuME, DOSBox, FCEUX, Fusion, MAME, melonDS, Mesen, MPC-HC, NO$GBA, SNES9X, VisualBoyAdvance-M)
         - Microsoft Office (Word, PowerPoint, Excel)
         - LibreOffice (Writer, Calc, Impress, Draw, Base, Math)
         - Media Player Classic
         - PotPlayer
         - MAGIX VEGAS Pro 14.0 - 22.0
         - DaVinci Resolve
         - VLC Media Player
         - Zoom Meetings
      * Credits go to: 
         * MechaDragonX on GitHub for DOSBox, FCEUX, Fusion, SNES9X, VisualBoyAdvance-M, MAME Discord Presence Application IDs
         * angeloanan on GitHub for MPC-HC Discord Presence Application IDs
         * FizzyApple12 on GitHub for Beat Saber Discord Presence Application IDs
         * shadoweG on GitHub for DaVinci Resolve Discord Presence Application IDs

    * Music (Windows/macOS):
      * Windows: Uses a Python Script with Embedded Python to grab the song information from the Windows Media Control Bar using WinRT API. Artwork is grabbed from TIDAL, and secondarily iTunes. For Windows 11 Users, you should read below what the current app IDs are to insert in musicProgram in the config.yml.
        * Specific Rich Presence Programs:
          - Amazon Music (does not display Timestamp)
          - Deezer
          - iTunes (iTunes Users should install [this](https://apps.microsoft.com/detail/9nq3d21qt8ml?hl=en-US&gl=US).)
          - Qobuz
          - TIDAL (does not display proper Timestamp)
        * Default Rich Presence:
          - Any program that shows information from the Windows Media Control Bar.
      * macOS: Uses a TypeScript Script to grab the song information from Apple Music/iTunes. Artwork is grabbed from iTunes.
      * Credits go to:
         * NextFire on GitHub for the original TypeScript Script Code and Apple Music, iTunes Discord Presence Application IDs
         * willf668 on GitHub for the original Python Script Code
         * Braasileiro for the Deezer Discord Presence Application ID
         * Lockna on GitHub for the Qobuz Discord Presence Application ID
         * purpl3F0x on GitHub for the TIDAL Discord Presence Application ID

    * Website (Chrome/Firefox Extension):
      * Uses the Chrome/Firefox Extension to grab the Website Title, URL and HTML to parse into Discord Presences.
         - Dailymotion
         - Disney+
         - Hulu
         - Instagram
         - Netflix
         - Twitch
         - Twitter
         - Wikipedia
         - YouTube
      * Credits go to PreMiD on GitHub for Website Discord Presence Application IDs

    * Beat Saber Presence:
      * Requires the HTTP Status Mod installed from ModAsssistant in order for it to work.
      * Credits go to FizzyApple12 on GitHub for Beat Saber Application IDs

    * Wiimmfi Mario Kart Wii Presence:
      * Uses your browser or a headless proxy server to grab data from Wiimmfi.de
      
    * Nintendo Switch 1/2 Auto Presence:
      * Uses the Nintendo Services and nxapi to grab what you are playing on Nintendo Switch.
      * You must use a secondary account that is friends with your account to grab the presence.
        * If you don't have one, you can make an alt account and add yourself and login to your alt account on the login page.

    * DeSmuME (Pokémon Gen 4):
      * Grabs data from a lua script to track map information on the emulator.
       * Supports only Diamond, Pearl, and Platinum ROMs. Credits go to kiwi515 on GitHub for the script
    
    * Media Player:
      * VLC Media Player:
        * Grabs media title, uses filename as fallback.
        * Uses the Lua HTTP Interface to grab media information, such as play state and position.
      * Media Player Classic:
        * Uses filename as title.
        * Uses the Web Interface to grab media information, such as play state and position.

## Windows 11 Music App IDs

The following are the App IDs if you are using the Music Presence. The ID should be specified in "musicProgram" in the config.yml

* Deezer: com.deezer.deezer-desktop
* Amazon Music: Amazon.Music
* TIDAL: com.squirrel.TIDAL.TIDAL
* Qobuz: com.squirrel.Qobuz.Qobuz
* iTunes: 49586DaveAntoine.MediaControllerforiTunes_9bzempp7dntjg!App

## Support

[Discord Server](https://discord.gg/nStuNeZsWz)

If you would like to request a program, game, website or presence to be added, you can join the Discord Server above and then clicking on the role selection LogRPC.

## Donations

[ko-fi.com Donation](https://ko-fi.com/Logicism)

Donations are much appreciated! Thank you for donating!
