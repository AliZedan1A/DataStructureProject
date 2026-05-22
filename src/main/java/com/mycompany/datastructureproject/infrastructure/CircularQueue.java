package com.mycompany.datastructureproject.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class CircularQueue {

    private String[] queue;

    private int front;
    private int rear;
    private int size;
    private int capacity;

    public CircularQueue(int capacity) {

        this.capacity = capacity;

        queue = new String[capacity];

        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
    public List<String> toList() {
          List<String> items = new ArrayList<>();

          int index = front;

          for (int i = 0; i < size; i++) {
              items.add(queue[index]);
              index = (index + 1) % capacity;
          }

          return items;
      }
    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(String value) {

        if (isFull()) {
            System.out.println("Queue is full");
            return;
        }

        rear = (rear + 1) % capacity;

        queue[rear] = value;

        size++;

        System.out.println(value + " added successfully");
    }

    public String dequeue() {

        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }

        String removed = queue[front];

        queue[front] = null;

        front = (front + 1) % capacity;

        size--;

        return removed;
    }

    public String peek() {

        if (isEmpty()) {
            return null;
        }

        return queue[front];
    }

    public void display() {

        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }

        int index = front;

        for (int i = 0; i < size; i++) {

            System.out.print(queue[index] + " ");

            index = (index + 1) % capacity;
        }

        System.out.println();
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }
}