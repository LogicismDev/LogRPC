# LogRPC

A Rich Presence program to show your activity on Discord

[Download for Windows (setup)](https://logicism.tv/downloads/LogRPC-setup.exe) - [Download for Windows (zip)](https://logicism.tv/downloads/LogRPC-v1.93-win64.zip) - [Download for macOS (dmg)](https://logicism.tv/downloads/LogRPC-v1.93-mac.dmg) - [Download for Linux (zip)](https://logicism.tv/downloads/LogRPC-v1.93-linux.zip) - [Download (Mirror)](https://mega.nz/folder/EIhkjCIR#Ck5IlMZeEtjVvBYjLNqrRw) (v1.93)

**Java 8 or higher is required in order to run LogRPC!**

* Presence Types:
    * Manual:
      - Default (config.yml) w/ Presets
      - Customizable Presence
      - Game Consoles:
          - Nintendo 3DS
          - Wii U
          - Nintendo Switch
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
         - MAGIX VEGAS Pro 14.0 - 20.0
         - DaVinci Resolve
         - VLC Media Player
         - Zoom Meetings
      * Credits go to: 
         * MechaDragonX on GitHub for DOSBox, FCEUX, Fusion, SNES9X, VisualBoyAdvance-M, MAME Discord Presence Application IDs
         * angeloanan on GitHub for MPC-HC Discord Presence Application IDs
         * FizzyApple12 on GitHub for Beat Saber Discord Presence Application IDs
         * shadoweG on GitHub for DaVinci Resolve Discord Presence Application IDs

    * Music (Windows/macOS):
      * Windows: Uses a Python Script with Embedded Python to grab the song information from the Windows Media Control Bar using WinRT API. Artwork is grabbed from TIDAL, and secondarily iTunes.
        * Specific Rich Presence Programs:
          - Amazon Music (does not display Timestamp)
          - Deezer
          - iTunes
          - Qobuz
          - TIDAL
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

    * DeSmuME (Pokémon Gen 4):
      * Grabs data from a lua script to track map information on the emulator.
       * Supports only Diamond, Pearl, and Platinum ROMs. Credits go to kiwi515 on GitHub for the script

If you would like to request a program, game, website or presence to be added, DM Logicism at his Discord (Logicism#9308)
