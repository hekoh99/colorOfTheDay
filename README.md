# Color of the Day

This repository contains the backend code for a web project that allows users to record their daily moods as colors. The backend is built with Spring Boot and utilizes a MySQL database to store and manage data.

## Table of Contents

- [Overview](#overview)
- [Database Structure](#database-structure)

  
## Overview

Color of the Day is a web application that lets users track their daily moods using colors. Each color represents a combination of emotions — achievement, stability, and fatigue. Users can view their mood history, identify patterns, and gain insights into their emotional well-being over time.

frontend code : https://github.com/hekoh99/colorOfDay_front

## Database Structure

The backend uses a MySQL database to store user mood data. To efficiently manage data load, a new table is created for each year and month (e.g., `colorlog_2024_08` for August 2024). This approach helps prevent excessive data accumulation in a single table, optimizing performance and scalability.

Each mood entry is stored as individual RGB values, where each RGB component reflects different emotions:
- **Red (R)**: Achievement
- **Green (G)**: Stability
- **Blue (B)**: Fatigue

These RGB values combine to form a representative color that reflects the user's mood for each day.

### Example Table Structure

For a given month, the table might look like this:
table name : colorlog_2024_08

| Date       | Red (R) | Green (G) | Blue (B) |
|------------|---------|-----------|----------|
| 1          | 120     | 200       | 150      |
| 2          | 180     | 100       | 90       |
| ...        | ...     | ...       | ...      |

