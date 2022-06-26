package com.canalplus.meetingplanner.util;


import com.canalplus.meetingplanner.exceptions.ReservationException;
import org.springframework.stereotype.Component;

@Component
public final class ChainBuilder<T> {

    public static <T> ChainBuilder<T> chainBuilder() {
        return new ChainBuilder<>();
    }

    private HandlerImpl<T> first;

    private ChainBuilder() {
    }

    public SuccessorBuilder first(final Handler<T> handler) {
        first = new HandlerImpl<>(handler);
        return new SuccessorBuilder(first);
    }

    @SuppressWarnings("PublicInnerClass")
    public final class SuccessorBuilder {

        private HandlerImpl<T> current;

        private SuccessorBuilder(final HandlerImpl<T> current) {
            this.current = current;
        }

        public SuccessorBuilder next(final Handler<T> next) {
            final HandlerImpl<T> successorWrapper = new HandlerImpl<>(next);
            current.setNext(successorWrapper);
            current = successorWrapper;
            return this;
        }

        public Chain<T> build() {
            return new ChainImpl<>(first);
        }
    }

    private static final class ChainImpl<T> implements Chain<T> {

        private final Handler<T> first;

        private ChainImpl(final Handler<T> first) {
            this.first = first;
        }

        @Override
        public void handle(final T t) throws ReservationException {
            first.handle(t);
        }
    }

    private static final class HandlerImpl<T> implements Handler<T> {

        private final Handler<T> delegate;
        private       Handler<T> next;

        private HandlerImpl(final Handler<T> delegate) {
            this.delegate = delegate;
        }

        private void setNext(final HandlerImpl<T> next) {
            this.next = next;
        }

        @Override
        public boolean handle(final T t) throws ReservationException {
            return delegate.handle(t) ||
                    next != null && next.handle(t);
        }
    }
}
