export function auth(fastify, { token }) {
  if (!token) {
    throw new Error('[In Risk] No token specified for the server');
  }

  fastify.addHook('preHandler', (request, reply, done) => {
    const t = request.headers.authorization?.replace('Bearer ', '');

    if (t !== token) {
      reply.send({ error: '访问未授权' });
    } else {
      done();
    }
  });
}
