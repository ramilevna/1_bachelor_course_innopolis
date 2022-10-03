#include <stdio.h>
#include <string.h>


int main() {
    // opening file to reading
    FILE *fp;
    fp = fopen("input.txt","r");
    int nameCounter = 0;
    // the total amount of names
    int n;
    fscanf(fp, "%d", &n);
    // checking that 1 <= n <= 100
    if (n < 1 || n > 100) {
        // closing file
        fclose(fp);
        // opening file to writing
        fp = fopen ("output.txt", "w");
        // writing to the file about error
        fputs("Error in the input.txt\n", fp);
        // closing file
        fclose(fp);
        return 0;
    }
    // checking that after n is "/n" not name
    char check;
    fscanf(fp, "%c", &check);
    if (check != '\n') {
        // closing file
        fclose(fp);
        // opening file to writing
        fp = fopen ("output.txt", "w");
        // writing to the file about error
        fputs("Error in the input.txt\n", fp);
        // closing file
        fclose(fp);
        return 0;
    }
    char names[109][109];
    // reading names from a file
    while ((fgets(names[nameCounter], 109, fp) != NULL)) {
        // checking for an empty string
        if (names[nameCounter][0] == '\n') {
            // closing file
            fclose(fp);
            // opening file to writing
            fp = fopen ("output.txt", "w");
            // writing to the file about error
            fputs("Error in the input.txt\n", fp);
            // closing file
            fclose(fp);
            return 0;
        }
        // changing the symbol "/n" for convenient working
        if (names[nameCounter][strlen(names[nameCounter]) - 1] == '\n'){
            names[nameCounter][strlen(names[nameCounter]) - 1] = '\0';}
        // checking that after name not space
        if (names[nameCounter][strlen(names[nameCounter]) - 1] == '\0') {
            // closing file
            fclose(fp);
            // opening file to writing
            fp = fopen ("output.txt", "w");
            // writing to the file about error
            fputs("Error in the input.txt\n", fp);
            // closing file
            fclose(fp);
            return 0;
        }
        // checking if all characters of the name except the first letter are english lowercase letters
        for (int j = 1; j < strlen(names[nameCounter]); j++) {
            if (names[nameCounter][j] < 'a' || names[nameCounter][j] > 'z') {
                // closing file
                fclose(fp);
                // opening file to writing
                fp = fopen ("output.txt", "w");
                // writing to the file about error
                fputs("Error in the input.txt\n", fp);
                // closing file
                fclose(fp);
                return 0;
            }
            // checking if the first letter is english uppercase letter
            if (names[nameCounter][0] < 'A' || names[nameCounter][0] > 'Z') {
                // closing file
                fclose(fp);
                // opening file to writing
                fp = fopen ("output.txt", "w");
                // writing to the file about error
                fputs("Error in the input.txt\n", fp);
                // closing file
                fclose(fp);
                return 0;
            }
        }
        // counting having added name
        nameCounter++;
    }
    // checking if value n which does not correspond to the number of names
    if (nameCounter != n) {
        // closing file
        fclose(fp);
        // opening file to writing
        fp = fopen ("output.txt", "w");
        // writing to the file about error
        fputs("Error in the input.txt\n", fp);
        // closing file
        fclose(fp);
        return 0;
    }
    // bubble sort
    for (int i = 0; i <= n - 1; i++) {
        for (int j = 0; j <= n - i - 2; j++) {
            // function compares two names
            if (strcmp(names[j], names[j + 1]) > 0)  {
                char swap[109];
                // swapping of elements
                strcpy(swap, names[j]);
                strcpy(names[j], names[j + 1]);
                strcpy(names[j + 1], swap);
            }
        }
    }
    // closing file
    fclose(fp);
    // opening file to writing
    fp = fopen ("output.txt", "w");
    // writing to file names
    for (int i = 0; i < n; i++) {
        fputs(names[i], fp);
        fputs("\n", fp);
    }
    // closing file
    fclose(fp);
    return 0;
}

