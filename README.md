
# League Application

Football League application is a simple app consisting of 3 screens to show a list of Competitions, teams in
each league and team information



## Authors

- [@OmarMahrous](https://www.github.com/OmarMahrous)


## API Reference

#### Get Competitions

```http://api.football-data.org/v2
  GET /competitions
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### getCompetitions(apiKey)

Takes apiKey and returns list of Competitions.


#### Get Competition details

```http
  GET /competitions/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `apiKey`      | `string` | **Required**. apiKey |
| `CompetitionID`      | `int` | **Required**. default = 2000 |

#### Get Teams

```http
  GET /competitions/{id}/teams
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `apiKey`      | `string` | **Required**. apiKey |
| `CompetitionID`      | `int` | **Required**. default = 2000 |

#### Get Team information

```http
  GET /teams/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `apiKey`      | `string` | **Required**. apiKey |
| `TeamID`      | `int` | **Required**. |


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

(You must generate an API-key using)
`X-Auth-Token` = "https://www.football-data.org/client/register"
Add 'X-Auth-Token' in the headers of your requests




## Demo

link to demo or APK

https://drive.google.com/file/d/1VBs7_cA3rzT18Uu5dSYAk6uDxZ0gXRRY/view?usp=share_link
## Features

- show a list of all the Competitions,
- Competition info (details) and seasons
- All the teams in selected Competitions,
- Show selected team information,
- Show team squad (payers details).


## Feedback

If you have any feedback, please reach out to us at Mahrous832@gmail.com


## 🚀 About Me
I worked on a lot of projects alone from A to Z and I solved many problems and learned many features although I am young and I am fresh graduated and I have 3 years of experience.

My strong skills:-
1 - Excellent Searcher and problem solver
2 - Advanced testing and debugging skills
3 - Attention to detail and continuous learning


## 🛠 Skills
OOP, 
Solid principles,
Restful APIs,
Real-time (Socket) API, 
Publishing Apps on the store, 
Multiple modules, 
Localization,
Data structures & Algorithms
, Android architecture,
 Material design (Animations),
 Testing and debugging,
Git version control.


## Appendix

This repository contains a sample app that implements MVVM architecture using Kotlin, ViewModel, Kotlin Flow,
Room database, Coroutines, Koin DI and etc.

The app has following packages:

1- data: It contains all the data accessing and manipulating components.

2- di: It implement dependency injection and contains modules using Koin.

3- ui: View classes along with their corresponding ViewModel.

4- util: Utility classes for whole app. 

(Their is nested utils packages in ui & data related to its super only)




## Roadmap

References:

// Intro to Koin Di
https://medium.com/android-beginners/koin-dependency-injection-framework-for-android-aa34c0737956

// Use retrofit with koin di
https://medium.com/@harmittaa/retrofit-2-6-0-with-koin-and-coroutines-4ff45a4792fc

// Unit testing with room database
https://medium.com/exploring-android/android-architecture-components-testing-your-room-dao-classes-e06e1c9a1535

Integration:

Open android project from Github

1 - Open your Android Studio then go to the File > New > Project from Version Control 

2 - After clicking on the Project from Version Control a pop-up screen will arise like below. In the Version control choose Git from the drop-down menu. 

3 - Then at last paste the link ("https://github.com/OmarMahrous/LeagueApplication.git") in the URL and choose your Directory. Click on the Clone button and you are done.