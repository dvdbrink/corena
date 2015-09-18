package com.danielvandenbrink.corena;

import java.util.ArrayList;
import java.util.List;

public class QuadTree<T extends Bounded> {
    private static final int DEFAULT_CAPACITY = 4;

    private final Bounds bounds;
    private final int capacity;

    private final List<QuadTree<T>> children = new ArrayList<>();
    private final List<T> elements = new ArrayList<>();

    public QuadTree(final Bounds bounds) {
        this(bounds, DEFAULT_CAPACITY);
    }

    public QuadTree(final Bounds bounds, final int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
    }

    public boolean insert(final T element) {
        if (!bounds.contains(element.bounds())) {
            return false;
        }

        if (elements.size() < capacity) {
            elements.add(element);
            return true;
        }

        if (children.isEmpty()) {
            subdivide();
        }

        for (QuadTree<T> child : children) {
            if (child.insert(element)) {
                return true;
            }
        }

        return false;
    }

    public List<T> query(final Bounds bounds) {
        final List<T> result = new ArrayList<>();

        if (!this.bounds.contains(bounds)) {
            return result;
        }

        for (final T element : elements) {
            if (bounds.contains(element.bounds())) {
                result.add(element);
            }
        }

        if (!children.isEmpty()) {
            for (final QuadTree<T> child : children) {
                result.addAll(child.query(bounds));
            }
        }

        return result;
    }

    private void subdivide() {
        children.add(new QuadTree<T>(
            new Bounds(
                bounds.x,
                bounds.y,
                bounds.width / 2f,
                bounds.height / 2f),
            capacity));

        children.add(new QuadTree<T>(
            new Bounds(
                bounds.x + bounds.width,
                bounds.y,
                bounds.width / 2f,
                bounds.height / 2f),
            capacity));

        children.add(new QuadTree<T>(
            new Bounds(
                bounds.x,
                bounds.y + bounds.height,
                bounds.width / 2f,
                bounds.height / 2f),
            capacity));

        children.add(new QuadTree<T>(
            new Bounds(
                bounds.x + bounds.width,
                bounds.y + bounds.height,
                bounds.width / 2f,
                bounds.height / 2f),
            capacity));
    }
}
