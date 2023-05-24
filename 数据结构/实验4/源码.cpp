#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define n 10 // 定义待排序表的长度为n

void printList(int list[], int length)
{
    for (int i = 0; i < length; i++)
    {
        printf("%d ", list[i]);
    }
    printf("\n");
}

void insertionSort(int list[], int length)
{
    for (int i = 1; i < length; i++)
    {
        int j = i;
        while (j > 0 && list[j] < list[j - 1])
        {
            int temp = list[j];
            list[j] = list[j - 1];
            list[j - 1] = temp;
            j--;
        }
    }
}

void bubbleSort(int list[], int length)
{
    for (int i = 0; i < length - 1; i++)
    {
        for (int j = 0; j < length - i - 1; j++)
        {
            if (list[j] > list[j + 1])
            {
                int temp = list[j];
                list[j] = list[j + 1];
                list[j + 1] = temp;
            }
        }
    }
}

void quickSort(int list[], int low, int high)
{
    if (low < high)
    {
        int pivot = list[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++)
        {
            if (list[j] <= pivot)
            {
                i++;
                int temp = list[i];
                list[i] = list[j];
                list[j] = temp;
            }
        }
        int temp = list[i + 1];
        list[i + 1] = list[high];
        list[high] = temp;

        quickSort(list, low, i);
        quickSort(list, i + 2, high);
    }
}

void selectionSort(int list[], int length)
{
    for (int i = 0; i < length - 1; i++)
    {
        int minIndex = i;
        for (int j = i + 1; j < length; j++)
        {
            if (list[j] < list[minIndex])
            {
                minIndex = j;
            }
        }
        int temp = list[i];
        list[i] = list[minIndex];
        list[minIndex] = temp;
    }
}

void heapify(int list[], int length, int i)
{
    int largest = i;
    int leftChild = 2 * i + 1;
    int rightChild = 2 * i + 2;

    if (leftChild < length && list[leftChild] > list[largest])
    {
        largest = leftChild;
    }

    if (rightChild < length && list[rightChild] > list[largest])
    {
        largest = rightChild;
    }

    if (largest != i)
    {
        int temp = list[i];
        list[i] = list[largest];
        list[largest] = temp;

        heapify(list, length, largest);
    }
}

void heapSort(int list[], int length)
{
    for (int i = length / 2 - 1; i >= 0; i--)
    {
        heapify(list, length, i);
    }

    for (int i = length - 1; i >= 0; i--)
    {
        int temp = list[0];
        list[0] = list[i];
        list[i] = temp;

        heapify(list, i, 0);
    }
}

void merge(int list[], int left, int middle, int right)
{
    int n1 = middle - left + 1;
    int n2 = right - middle;

    int leftList[n1], rightList[n2];

    for (int i = 0; i < n1; i++)
    {
        leftList[i] = list[left + i];
    }
    for (int j = 0; j < n2; j++)
    {
        rightList[j] = list[middle + 1 + j];
    }

    int i = 0;
    int j = 0;
    int k = left;

    while (i < n1 && j < n2)
    {
        if (leftList[i] <= rightList[j])
        {
            list[k] = leftList[i];
            i++;
        }
        else
        {
            list[k] = rightList[j];
            j++;
        }
        k++;
    }

    while (i < n1)
    {
        list[k] = leftList[i];
        i++;
        k++;
    }

    while (j < n2)
    {
        list[k] = rightList[j];
        j++;
        k++;
    }
}

void mergeSort(int list[], int left, int right)
{
    if (left < right)
    {
        int middle = left + (right - left) / 2;

        mergeSort(list, left, middle);
        mergeSort(list, middle + 1, right);

        merge(list, left, middle, right);
    }
}


int main()
{
    int testSizes[] = {10000, 20000};
    for (int i = 0; i < sizeof(testSizes) / sizeof(int); i++)
    {
        int size = testSizes[i];
        printf("当测试数据为%d时，显示结果为：\n", size);

        int list[size];
        for (int j = 0; j < size; j++)
        {
            list[j] = rand() % size;
        }

        clock_t start, end;
        double duration;

        start = clock();
        insertionSort(list, size);
        end = clock();
        duration = (double)(end - start) / CLOCKS_PER_SEC * 1000;
        printf("直接插入排序的时间为：%.2f 毫秒\n", duration);

        for (int j = 0; j < size; j++)
        {
            list[j] = rand() % size;
        }

        start = clock();
        bubbleSort(list, size);
        end = clock();
        duration = (double)(end - start) / CLOCKS_PER_SEC * 1000;
        printf("冒泡排序的时间为：%.2f 毫秒\n", duration);

        for (int j = 0; j < size; j++)
        {
            list[j] = rand() % size;
        }

        start = clock();
        quickSort(list, 0, size - 1);
        end = clock();
        duration = (double)(end - start) / CLOCKS_PER_SEC * 1000;
        printf("快速排序的时间为：%.2f 毫秒\n", duration);

        for (int j = 0; j < size; j++)
        {
            list[j] = rand() % size;
        }

        start = clock();
        selectionSort(list, size);
        end = clock();
        duration = (double)(end - start) / CLOCKS_PER_SEC * 1000;
        printf("直接选择排序的时间为：%.2f 毫秒\n", duration);

        for (int j = 0; j < size; j++)
        {
            list[j] = rand() % size;
        }

        start = clock();
        heapSort(list, size);
        end = clock();
        duration = (double)(end - start) / CLOCKS_PER_SEC * 1000;
        printf("堆排序的时间为：%.2f 毫秒\n\n", duration);
    }
    return 0;
}


