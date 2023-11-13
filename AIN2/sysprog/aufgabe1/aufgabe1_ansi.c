#include <stdio.h>

int main(void)
{
    /*
     * long long int und unsigned long long int sowie den Datentyp bool bzw. _Bool gab es in C89 noch nicht.
     */
    int i = 123;
    short h = 321;
    long li = 345L;
    unsigned int ui = 0xffffffffU;
    unsigned short us = 0xffffU;
    unsigned long uli = 384982;
    float f = 3.4F;
    double d = 3.5;
    char c = 'c';
    signed char sc = -1;
    unsigned char uc = 0xff;
    const char *s = "Hallo";


    printf("Adresse\t\t\tPlatzbedarf\tTyp\t\t\tName\t\tWert\n");


    printf("%p\t\t%lu\t\tint\t\t\ti\t\t%d\n",
           (void*)&i, (unsigned long)sizeof(int), i);

    printf("%p\t\t%lu\t\tshort\t\t\th\t\t%d\n",
           (void*)&h, (unsigned long)sizeof(short), h);

    printf("%p\t\t%lu\t\tlong int\t\tli\t\t%ld\n",
           (void*)&li, (unsigned long)sizeof(long int), li);

    printf("%p\t\t%lu\t\tunsigned int\t\tui\t\t%u\n",
           (void*)&ui, (unsigned long)sizeof(unsigned int), ui);

    printf("%p\t\t%lu\t\tunsigned short\t\tus\t\t%u\n",
           (void*)&us, (unsigned long)sizeof(unsigned short), us);

    printf("%p\t\t%lu\t\tunsigned long int\tuli\t\t%lu\n", (void*)&uli,
           (unsigned long)sizeof(unsigned long), uli);

    printf("%p\t\t%lu\t\tfloat\t\t\tf\t\t%f\n",
           (void*)&f, (unsigned long)sizeof(float), f);

    printf("%p\t\t%lu\t\tdouble\t\t\td\t\t%f\n",
           (void*)&d, (unsigned long)sizeof(double), d);

    printf("%p\t\t%lu\t\tchar\t\t\tc\t\t%c\n",
           (void*)&c, (unsigned long)sizeof(char), c);

    printf("%p\t\t%lu\t\tsigned char\t\tsc\t\t%d\n",
           (void*)&sc, (unsigned long)sizeof(signed char), sc);

    printf("%p\t\t%lu\t\tunsigned char\t\tuc\t\t%u\n",
           (void*)&uc, (unsigned long)sizeof(unsigned char), uc);

    printf("%p\t\t%lu\t\tsigned char\t\t*s\t\t%s\n",
           (void*)&s, (unsigned long)sizeof(signed char), s);
    return 0;
}
