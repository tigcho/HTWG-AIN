#include <stdio.h>
#include <stdbool.h>
/*
 * %p ist Platzhalter fuer Adressen
 * %zu ist Laengenanpassung fuer sizeof-Werte
 * &_name_ liefert die Adresse einer Variablen
 * Der Zeiger hat den Typ void, weil die Groesse der Variablen je nach Architektur
 * variieren kann.
 */

int main(void)
{
    printf("Adresse\t\t\tPlatzbedarf\tTyp\t\t\tName\t\tWert\n");

    int i = 123;
    printf("%p\t\t%zu\t\tint\t\t\ti\t\t%d\n",
           (void*)&i, sizeof(int), i);

    short h = 321;
    printf("%p\t\t%zu\t\tshort\t\t\th\t\t%hd\n",
           (void*)&h, sizeof(short), h);

    long li = 345L;
    printf("%p\t\t%zu\t\tlong int\t\tli\t\t%ld\n",
           (void*)&li, sizeof(long int), li);

    long long lli = -789LL;
    printf("%p\t\t%zu\t\tlong long int\t\tlli\t\t%lld\n",
           (void*)&lli, sizeof(long long int), lli);

    unsigned int ui = 0xffffffffU;
    printf("%p\t\t%zu\t\tunsigned int\t\tui\t\t%u\n",
           (void*)&ui, sizeof(unsigned int), ui);

    unsigned short us = 0xffffU;
    printf("%p\t\t%zu\t\tunsigned short\t\tus\t\t%hu\n",
           (void*)&us, sizeof(unsigned short), us);

    unsigned long uli = 384982;
    printf("%p\t\t%zu\t\tunsigned long int\tuli\t\t%lu\n", (void*)&uli,
           sizeof(unsigned long int), uli);

    unsigned long long ulli = 18273;
    printf("%p\t\t%zu\t\tunsigned long long int\tulli\t\t%llu\n", (void*)&ulli,
           sizeof(unsigned long long int), ulli);

    float f = 3.4F;
    printf("%p\t\t%zu\t\tfloat\t\t\tf\t\t%f\n",
           (void*)&f, sizeof(float), f);

    double d = 3.5;
    printf("%p\t\t%zu\t\tdouble\t\t\td\t\t%lf\n",
           (void*)&d, sizeof(double), d);

    long double ld = 3.45L;
    printf("%p\t\t%zu\t\tlong double\t\tld\t\t%Lf\n",
           (void*)&ld, sizeof(long double), ld);

    char c = 'c';
    printf("%p\t\t%zu\t\tchar\t\t\tc\t\t%c\n",
           (void*)&c, sizeof(char), c);


    signed char sc = -1;
    printf("%p\t\t%zu\t\tsigned char\t\tsc\t\t%hhd\n",
           (void*)&sc, sizeof(signed char), sc);

    unsigned char uc = 0xff;
    printf("%p\t\t%zu\t\tunsigned char\t\tuc\t\t%hhu\n",
           (void*)&uc, sizeof(unsigned char), uc);

    bool b = true;
    printf("%p\t\t%zu\t\tbool\t\t\tb\t\t%d\n",
           (void*)&b, sizeof(bool), b);

    const char *s = "Hallo";
    printf("%p\t\t%zu\t\tsigned char\t\t*s\t\t%s\n",
           (void*)&s, sizeof(signed char), s);

    if (sizeof(int) == 4 && sizeof(long int) == 4 && sizeof(char*) == 4)
        printf("\nILP32\n");
    else if (sizeof(int) == 4 && sizeof(long int) == 8 && sizeof(char*) == 8)
        printf("\nLP64\n");
    else if (sizeof(int) == 4 && sizeof(long int) == 4 && sizeof(long long int) == 8 && sizeof(char*) == 8)
        printf("\nLLP64\n");
    else
        printf("\nEs handelt sich um keins der drei Modelle\n");
    return 0;
}