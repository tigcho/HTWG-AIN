#include <stdio.h>
#include <inttypes.h>

int main(void)
{
    printf("Adresse\t\t\tPlatzbedarf\tTyp\t\tName\tWert\n");

    int8_t i8 = INT8_MAX;
    printf("%p\t\t%zu\t\tint8_t\t\ti8\t%" PRId8 "\n",
           (void*)&i8, sizeof(int8_t), i8);

    int16_t i16 = INT16_MAX;
    printf("%p\t\t%zu\t\tint16_t\t\ti16\t%" PRId16 "\n",
           (void*)&i16, sizeof(int16_t), i16);

    int32_t i32 = INT32_MAX;
    printf("%p\t\t%zu\t\tint32_t\t\ti32\t%" PRId32 "\n",
           (void*)&i32, sizeof(int32_t), i32);

    int64_t i64 = INT64_MAX;
    printf("%p\t\t%zu\t\tint64_t\t\ti64\t%" PRId64 "\n",
           (void*)&i64, sizeof(int64_t), i64);

    intmax_t imax = INTMAX_MAX;
    printf("%p\t\t%zu\t\tintmax_t\timax\t%" PRIdMAX "\n",
           (void*)&imax, sizeof(intmax_t), imax);

    uint8_t ui8 = UINT8_MAX;
    printf("%p\t\t%zu\t\tuint8_t\t\tui8\t%" PRIu8 "\n",
           (void*)&ui8, sizeof(uint8_t), ui8);

    uint16_t ui16 = UINT16_MAX;
    printf("%p\t\t%zu\t\tuint16_t\tui16\t%" PRIu16 "\n",
           (void*)&ui16, sizeof(uint16_t), ui16);

    uint32_t ui32 = UINT32_MAX;
    printf("%p\t\t%zu\t\tuint32_t\tui32\t%" PRIu32 "\n",
           (void*)&ui32, sizeof(uint32_t), ui32);

    uint64_t ui64 = UINT64_MAX;
    printf("%p\t\t%zu\t\tuint64_t\tui64\t%" PRIu64 "\n",
           (void*)&ui64, sizeof(uint64_t), ui64);

    uintmax_t uimax = UINTMAX_MAX;
    printf("%p\t\t%zu\t\tuintmax_t\tuimax\t%" PRIuMAX "\n",
           (void*)&uimax, sizeof(uintmax_t), uimax);

    int_least8_t il8 = INT_LEAST8_MAX;
    printf("%p\t\t%zu\t\tint_least8_t\til8\t%" PRIdLEAST8 "\n",
           (void*)&il8, sizeof(int_least8_t), il8);

    int_least16_t il16 = INT_LEAST16_MAX;
    printf("%p\t\t%zu\t\tint_least16_t\til16\t%" PRIdLEAST16 "\n",
           (void*)&il16, sizeof(int_least16_t), il16);

    int_least32_t il32 = INT_LEAST32_MAX;
    printf("%p\t\t%zu\t\tint_least32_t\til32\t%" PRIdLEAST32 "\n",
           (void*)&il32, sizeof(int_least32_t), il32);

    int_least64_t il64 = INT_LEAST64_MAX;
    printf("%p\t\t%zu\t\tint_least64_t\til64\t%" PRIdLEAST64 "\n",
           (void*)&il64, sizeof(int_least64_t), il64);

    uint_least8_t uil8 = UINT_LEAST8_MAX;
    printf("%p\t\t%zu\t\tuint_least8_t\tuil8\t%" PRIuLEAST8 "\n",
           (void*)&uil8, sizeof(uint_least8_t), uil8);

    uint_least16_t uil16 = UINT_LEAST16_MAX;
    printf("%p\t\t%zu\t\tuint_least16_t\til16\t%" PRIuLEAST16 "\n",
           (void*)&uil16, sizeof(uint_least16_t), uil16);

    uint_least32_t uil32 = UINT_LEAST32_MAX;
    printf("%p\t\t%zu\t\tuint_least32_t\til32\t%" PRIuLEAST32 "\n",
           (void*)&uil32, sizeof(uint_least32_t), uil32);

    uint_least64_t uil64 = UINT_LEAST64_MAX;
    printf("%p\t\t%zu\t\tuint_least64_t\til64\t%" PRIuLEAST64 "\n",
           (void*)&uil64, sizeof(uint_least64_t), uil64);

    int_fast8_t if8 = INT_FAST8_MAX;
    printf("%p\t\t%zu\t\tint_fast8_t\tif8\t%" PRIdFAST8 "\n",
           (void*)&if8, sizeof(int_fast8_t), if8);

    int_fast16_t if16 = INT_FAST16_MAX;
    printf("%p\t\t%zu\t\tint_fast16_t\tif16\t%" PRIdFAST16 "\n",
           (void*)&if16, sizeof(int_fast16_t), if16);

    int_fast32_t if32 = INT_FAST32_MAX;
    printf("%p\t\t%zu\t\tint_fast32_t\tif32\t%" PRIdFAST32 "\n",
           (void*)&if32, sizeof(int_fast32_t), if32);

    int_fast64_t if64 = INT_FAST64_MAX;
    printf("%p\t\t%zu\t\tint_fast64_t\tif64\t%" PRIdFAST64 "\n",
           (void*)&if64, sizeof(int_fast64_t), if64);

    uint_fast8_t uif8 = UINT_FAST8_MAX;
    printf("%p\t\t%zu\t\tuint_fast8_t\tuif8\t%" PRIuFAST8 "\n",
           (void*)&uif8, sizeof(uint_fast8_t), uif8);

    uint_fast16_t uif16 = UINT_FAST16_MAX;
    printf("%p\t\t%zu\t\tuint_fast16_t\tuif16\t%" PRIuFAST16 "\n",
           (void*)&uif16, sizeof(uint_fast16_t), uif16);

    uint_fast32_t uif32 = UINT_FAST32_MAX;
    printf("%p\t\t%zu\t\tuint_fast32_t\tuif32\t%" PRIuFAST32 "\n",
           (void*)&uif32, sizeof(uint_fast32_t), uif32);

    uint_fast64_t uif64 = UINT_FAST64_MAX;
    printf("%p\t\t%zu\t\tuint_fast64_t\tuif64\t%" PRIuFAST64 "\n",
           (void*)&uif64, sizeof(uint_fast64_t), uif64);
}

