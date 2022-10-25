#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct S {
    char name[109];
    int team;
    int power;
    char vi[109];
};


int main() {
    // opening file to reading
    // opening file to writing
    FILE *fpIn;
    FILE *fpOut;
    fpIn = fopen("input.txt","r");
    fpOut = fopen ("output.txt", "w");
    int magiciansCounter = 0;
    // the total amount of magicians
    int n;
    fscanf(fpIn, "%d\n", &n);
    // checking that 1 <= n <= 10
    if (n < 1 || n > 10) {
        // writing to the file about error
        fputs("Invalid inputs\n", fpOut);
        // closing files
        fclose(fpIn);
        fclose(fpOut);
        return 0;
    }
    char magicians[109][109];
    // reading names of magicians from a file
    for (int i = 0; i < n; i++) {
        fscanf(fpIn, "%s\n", magicians[magiciansCounter]);
        int l = strlen(magicians[magiciansCounter]);
        // checking that 2 <= l <= 20
        if (l < 2 || l > 20) {
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        // checking if the first letter is english uppercase letter
        if (magicians[magiciansCounter][0] < 'A' || magicians[magiciansCounter][0] > 'Z') {
            // writing to the file about error
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        // checking if all characters of the name are english letters
        for (int j = 1; j < l; j++) {
            if ((magicians[magiciansCounter][j] < 'a' || magicians[magiciansCounter][j] > 'z') && (magicians[magiciansCounter][j] < 'A' || magicians[magiciansCounter][j] > 'Z')) {
                // writing to the file about error
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
        }
        // counting having added name
        magiciansCounter++;
    }
    // the total amount of name
    int m;
    fscanf(fpIn, "%d\n", &m);
    // checking that n <= m <= 100
    if (m < n || m > 100) {
        // writing to the file about error
        fputs("Invalid inputs\n", fpOut);
        // closing files
        fclose(fpIn);
        fclose(fpOut);
        return 0;
    }
    // array of structures of players
    struct S s[m];
    int playersCounter = 0;
    // reading players and their data from the file
    for (int i = 0; i < m; i++) {
        fscanf(fpIn, "%s\n", s[playersCounter].name);
        int l = strlen(s[playersCounter].name);
        // checking that 2 <= l <= 20
        if (l < 2 || l > 20) {
            // writing to the file about error
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        // checking if the first letter is english uppercase letter
        if (s[playersCounter].name[0] < 'A' || s[playersCounter].name[0] > 'Z') {
            // writing to the file about error
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        // checking if all characters of the name are english letters
        for (int j = 1; j < l; j++) {
            if ((s[playersCounter].name[j] < 'a' || s[playersCounter].name[j] > 'z') && (s[playersCounter].name[j] < 'A' || s[playersCounter].name[j] > 'Z')) {
                // writing to the file about error
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
        }
        fscanf(fpIn, "%d\n", &s[playersCounter].team);
        // checking that 0 <= s[playersCounter].team < n
        if (s[playersCounter].team < 0 || s[playersCounter].team >= n) {
            // writing to the file about error
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        fscanf(fpIn, "%d\n", &s[playersCounter].power);
        // checking that 0 <= s[playersCounter].power <= 1000
        if (s[playersCounter].power < 0 || s[playersCounter].power > 1000) {
            // writing to the file about error
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
        fscanf(fpIn, "%s\n", s[playersCounter].vi);
        playersCounter++;
    }
    char str[109];
    int actionsCounter = 0;
    int supersCounter = -1;
    char supersCounterStr[109];
    // reading actions from the file
    while (fscanf(fpIn,"%s\n", str) != EOF) {
        actionsCounter++;
        char playerStr1[109];
        char playerStr2[109];
        int player1 = -1;
        int player2 = -1;
        if (strcmp(str, "attack") == 0) {
            // reading first name of player
            fscanf(fpIn,"%s\n", playerStr1);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr1) == 0) {
                    player1 = i;
                }
            }
            // reading second name of player
            fscanf(fpIn,"%s", playerStr2);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr2) == 0) {
                    player2 = i;
                }
            }
            // checking that players exist
            if (player1 == -1 || player2 == -1) {
                // writing to the file about error
                fclose(fpOut);
                fpOut = fopen ("output.txt", "w");
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
            if (strcmp(s[player1].vi, "False") == 0) {
                fputs("This player can't play\n", fpOut);
                continue;
            }
            if (s[player1].power == 0) {
                fputs("This player is frozen\n", fpOut);
                continue;
            }
            if (strcmp(s[player2].vi, "False") == 0) {
                s[player1].power = 0;
            }
                // attack
            else if (s[player1].power > s[player2].power) {
                if (s[player1].power + (s[player1].power - s[player2].power) > 1000) {
                    s[player1].power = 1000;
                }
                else {
                    s[player1].power += (s[player1].power - s[player2].power);
                }
                s[player2].power = 0;
            }
            else if (s[player1].power < s[player2].power) {
                if (s[player2].power + (s[player2].power - s[player1].power) > 1000) {
                    s[player2].power = 1000;
                }
                else {
                    s[player2].power += (s[player2].power - s[player1].power);
                }
                s[player1].power = 0;
            }
            else if (s[player1].power == s[player2].power) {
                s[player1].power = 0;
                s[player2].power = 0;
            }
        }
        else if (strcmp(str, "flip_visibility") == 0) {
            fscanf(fpIn,"%s\n", playerStr1);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr1) == 0) {
                    player1 = i;
                }
            }
            // checking that the player exist
            if (player1 == -1) {
                // writing to the file about error
                fclose(fpOut);
                fpOut = fopen ("output.txt", "w");
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
            if (s[player1].power == 0) {
                fputs("This player is frozen\n", fpOut);
                continue;
            }
            // changing visibility
            if (strcmp(s[player1].vi, "True") == 0) {
                strcpy(s[player1].vi, "False");
            }
            else {
                strcpy(s[player1].vi, "True");
            }
        }
        else if (strcmp(str, "heal") == 0){
            fscanf(fpIn,"%s\n", playerStr1);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr1) == 0) {
                    player1 = i;
                }
            }
            fscanf(fpIn,"%s", playerStr2);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr2) == 0) {
                    player2 = i;
                }
            }
            // checking that players exist
            if (player1 == -1 || player2 == -1) {
                // writing to the file about error
                fclose(fpOut);
                fpOut = fopen ("output.txt", "w");
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
            if (strcmp(s[player1].vi, "False") == 0) {
                fputs("This player can't play\n", fpOut);
                continue;
            }
            if (s[player1].power == 0) {
                fputs("This player is frozen\n", fpOut);
                continue;
            }
            if (s[player1].team != s[player2].team) {
                fputs("Both players should be from the same team\n", fpOut);
                continue;
            }
            if (player1 == player2) {
                fputs("The player cannot heal itself\n", fpOut);
                continue;
            }
            // heal
            s[player2].power += (s[player1].power +1)/2;
            s[player1].power = (s[player1].power +1)/2;
            if (s[player2].power > 1000){
                s[player2].power = 1000;
            }
        }
            // super
        else if (strcmp(str, "super") == 0) {
            char nameSuper[109] = "S_";
            supersCounter++;
            fscanf(fpIn,"%s\n", playerStr1);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr1) == 0) {
                    player1 = i;
                }
            }
            fscanf(fpIn,"%s", playerStr2);
            for (int i = 0; i < m; i++) {
                if (strcmp(s[i].name, playerStr2) == 0) {
                    player2 = i;
                }
            }
            // checking that players exist
            if (player1 == -1 || player2 == -1) {
                // writing to the file about error
                fclose(fpOut);
                fpOut = fopen ("output.txt", "w");
                fputs("Invalid inputs\n", fpOut);
                // closing files
                fclose(fpIn);
                fclose(fpOut);
                return 0;
            }
            if (strcmp(s[player1].vi, "False") == 0) {
                fputs("This player can't play\n", fpOut);
                continue;
            }
            if (s[player1].power == 0) {
                fputs("This player is frozen\n", fpOut);
                continue;
            }
            if (s[player1].team != s[player2].team) {
                fputs("Both players should be from the same team\n", fpOut);
                continue;
            }
            if (strcmp(s[player1].name, s[player2].name)==0) {
                fputs("The player cannot do super action with itself\n", fpOut);
                continue;
            }
            // making a superplayer and delating old players

            /*itoa(supersCounter, supersCounterStr, 10);
            for (int i = 0; i < strlen(supersCounterStr); i++) {
                nameSuper[2 + i] = supersCounterStr[i];
            }
            strcpy(s[player1].name, nameSuper);*/
            sprintf(s[player1].name,"S_%d",supersCounter);
            strcpy(s[player1].vi, "True");
            if (s[player1].power + s[player2].power > 1000) {
                s[player1].power = 1000;
            }
            else {
                s[player1].power += s[player2].power;
            }
            strcpy(s[player2].name, "00");
            s[player2].team = 0;
            s[player2].power = 0;
            strcpy(s[player2].vi, "00");
        }
        else {
            // writing to the file about error
            fclose(fpOut);
            fpOut = fopen ("output.txt", "w");
            fputs("Invalid inputs\n", fpOut);
            // closing files
            fclose(fpIn);
            fclose(fpOut);
            return 0;
        }
    }
    // checking that 0 <= actions <= 1000
    if (actionsCounter < 0 || actionsCounter > 1000) {
        // writing to the file about error
        fclose(fpOut);
        fpOut = fopen ("output.txt", "w");
        fputs("Invalid inputs\n", fpOut);
        // closing files
        fclose(fpIn);
        fclose(fpOut);
        return 0;
    }
    // creating an array for sums of powers
    int powers[n];
    for (int i = 0; i < n; i++) {
        powers[i] = 0;
    }
    for (int i = 0; i < m; i++) {
        powers[s[i].team] += s[i].power;
    }
    // looking for the maximum value
    int maxIndex=0, duplicate = 0;
    for (int i = 1; i <= n - 1; i++) {
        if(powers[i] > powers[maxIndex]){
            maxIndex = i;
            duplicate = 0;
        }else if(powers[i] == powers[maxIndex]){
            duplicate = 1;
        }
    }
    // checking for a tie
    if (duplicate == 1) {
        fputs("It's a tie\n", fpOut);
    }
    else {
        fputs("The chosen wizard is ", fpOut);
        fputs(magicians[maxIndex], fpOut);
        fputs("\n", fpOut);
    }
    // closing files
    fclose(fpIn);
    fclose(fpOut);
}