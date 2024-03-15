![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)

## In Touch - Корпоративный мессенджер

Мобильное клиентское приложение, разрабатываемое в качестве курсовой работы на 3ей курсе.

Ссылки на связанные репозитрии:
* [Backend](https://github.com/Zeonx9/chat-server-demo)
* [Windows desktop client](https://github.com/Zeonx9/chat-client-demo)
* [Design - figma](https://www.figma.com/file/X4x3xxTJln2hWVLn6cdp9x/mobile_design?type=design&node-id=0%3A1&mode=design&t=Cp2elcHBve0NcPSn-1)

## Использованные библиотеки

Проект написан на Kotlin. UI написан с помощью Jetpack Compose.
Также испоьзованы:

* Dagger-Hilt - для внедрения зависимостей
* Retrofit - для работы с API по сети
* Compose Navigation - для навигации между экранами
* StompProtocolAndroid + RxJava - для WebSocket подключения к серверу и рекции на события

## Функционал приложения

1. Общение в личных чатах и создание группвых чатов внутри компании